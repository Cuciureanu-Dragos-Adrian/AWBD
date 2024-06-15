package app.restman.api.controllers;

import app.restman.api.DTOs.TableCreateDTO;
import app.restman.api.DTOs.TableReturnDTO;
import app.restman.api.DTOs.TableUpdateDTO;
import app.restman.api.entities.Table;
import app.restman.api.services.TableService;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("tables")
@CrossOrigin(origins = "*")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTables() {
        List<Table> tables = tableService.getAllTables();
        List<TableReturnDTO> tableDTOs = tables.stream()
                .map(table -> {
                    TableReturnDTO dto = new TableReturnDTO(table);
                    Link selfLink = linkTo(methodOn(TableController.class).getTableById(dto.getTableId())).withSelfRel();
                    Link updateLink = linkTo(methodOn(TableController.class).updateTable(dto.getTableId(), null)).withRel("update");
                    Link deleteLink = linkTo(methodOn(TableController.class).deleteTable(dto.getTableId())).withRel("delete");

                    dto.add(selfLink);
                    dto.add(updateLink);
                    dto.add(deleteLink);
                    return dto;
                })
                .collect(Collectors.toList());

        Link link = linkTo(methodOn(TableController.class).getAllTables()).withSelfRel();
        CollectionModel<TableReturnDTO> result = CollectionModel.of(tableDTOs, link);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableReturnDTO> getTableById(@PathVariable String id) {
        Table table = tableService.getTableById(id);
        if (table != null) {
            TableReturnDTO result = new TableReturnDTO(table);
            Link selfLink = linkTo(methodOn(TableController.class).getTableById(result.getTableId())).withSelfRel();
            Link updateLink = linkTo(methodOn(TableController.class).updateTable(result.getTableId(), null)).withRel("update");
            Link deleteLink = linkTo(methodOn(TableController.class).deleteTable(result.getTableId())).withRel("delete");

            result.add(selfLink);
            result.add(updateLink);
            result.add(deleteLink);

            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createTable(@RequestBody TableCreateDTO tableCreateDTO) {
        try {
            Table createdTable = tableService.createTable(tableCreateDTO);
            TableReturnDTO dto = new TableReturnDTO(createdTable);
            Link selfLink = linkTo(methodOn(TableController.class).getTableById(createdTable.getTableId())).withSelfRel();
            dto.add(selfLink);

            return new ResponseEntity<>(dto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{tableId}")
    public ResponseEntity<?> updateTable(@PathVariable String tableId, @RequestBody TableUpdateDTO updatedTable) {
        try {
            tableService.updateTable(tableId, updatedTable);
            Table updated = tableService.getTableById(tableId);
            TableReturnDTO dto = new TableReturnDTO(updated);
            Link selfLink = linkTo(methodOn(TableController.class).getTableById(updated.getTableId())).withSelfRel();
            dto.add(selfLink);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{tableId}")
    public ResponseEntity<Void> deleteTable(@PathVariable String tableId) {
        try {
            tableService.deleteTable(tableId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
