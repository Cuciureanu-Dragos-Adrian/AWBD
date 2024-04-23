package app.restman.api.controllers;

import app.restman.api.entities.TableEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("tables")
public class TableController {

    // This is just a placeholder for your actual data source
    private final List<TableEntity> tables = new ArrayList<>();

    // Constructor to initialize some dummy data
    public TableController() {
        // Add some dummy data to the list
        tables.add(new TableEntity("1", 10.0, 20.0, 4, 1));
        tables.add(new TableEntity("2", 15.0, 25.0, 6, 2));
        // Add more tables as needed
    }

    @GetMapping("/getAll")
    public List<TableEntity> getAllTables() {

        //TODO - get tables from somewhere

        // Return the list of tables
        return tables;
    }

    @PostMapping("/add")
    public void addTable(@RequestBody TableEntity tableEntity)
    {
        //TODO - save changes

        tables.add(tableEntity);
    }

    @PutMapping("/{id}")
    public void updateReservation(@PathVariable String id, @RequestBody TableEntity updatedTable) {
        for (int index = 0; index < tables.size(); index++) {
            TableEntity table = tables.get(index);
            if (table.getTableId().equals(id)) {
                tables.set(index, updatedTable);
                //TODO - save changes
                break; // Assuming table IDs are unique, we can exit the loop after replacing the table
            }
        }
    }

    @PutMapping("/editPosition")
    public void editTablePosition(@RequestParam String id,
                                  @RequestParam double xOffset,
                                  @RequestParam double yOffset) {
        for (TableEntity table : tables) {
            if (table.getTableId().equals(id)) {
                table.setXOffset(xOffset);
                table.setYOffset(yOffset);
            }
        }

        //TODO - update said table
    }

    @DeleteMapping("/{id}")
    public void deleteTableById(@PathVariable String id) {
        Iterator<TableEntity> iterator = tables.iterator();
        while (iterator.hasNext()) {
            TableEntity table = iterator.next();
            if (table.getTableId().equals(id)) {
                iterator.remove();
                //TODO - delete table
                break; // Assuming IDs are unique, we can exit loop after finding the match
            }
        }
    }
}
