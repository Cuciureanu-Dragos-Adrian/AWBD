package app.restman.api.controllers;

import app.restman.api.DTOs.TableCreateDTO;
import app.restman.api.DTOs.TableReturnDTO;
import app.restman.api.DTOs.TableUpdateDTO;
import app.restman.api.entities.Table;
import app.restman.api.services.TableService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tables")
public class TableController {

    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<TableReturnDTO>> getAllTables() {
        List<Table> tables = tableService.getAllTables();
        List<TableReturnDTO> tableDTOs = tables.stream()
                .map(TableReturnDTO::new) // Convert each Table to TableDTO
                .collect(Collectors.toList());
        return new ResponseEntity<>(tableDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableReturnDTO> getTableById(@PathVariable String id) {
        Table table = tableService.getTableById(id);
        if (table != null) {
            return new ResponseEntity<>(new TableReturnDTO(table), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createTable(@RequestBody TableCreateDTO tableCreateDTO) {
        try {
            Table createdTable = tableService.createTable(tableCreateDTO);
            return new ResponseEntity<>(createdTable, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{tableId}")
    public ResponseEntity<?> updateTable(@PathVariable String tableId, @RequestBody TableUpdateDTO updatedTable) {

        try {
            tableService.updateTable(tableId, updatedTable);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{tableId}")
    public ResponseEntity<Void> deleteTable(@PathVariable String tableId) {
        try {
            tableService.deleteTable(tableId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}