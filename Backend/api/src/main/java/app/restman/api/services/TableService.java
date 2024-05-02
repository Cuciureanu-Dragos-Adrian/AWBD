package app.restman.api.services;

import app.restman.api.DTOs.TableCreateDTO;
import app.restman.api.DTOs.TableUpdateDTO;
import app.restman.api.entities.Table;
import app.restman.api.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class TableService {

    private final TableRepository tableRepository;

    @Autowired
    public TableService(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    private static final Logger logger = Logger.getLogger(TableService.class.getName());

    public List<Table> getAllTables() {
        return tableRepository.findAll();
    }

    public Table getTableById(String id) {
        return tableRepository.findById(id).orElse(null);
    }

    public Table createTable(TableCreateDTO newTable) throws Exception {

        if (newTable.getTableSize() < 1){
            logger.log(Level.SEVERE, "Table size cannot be less than 1!");
            throw new Exception("Table size cannot be less than 1!");
        }

        if (newTable.getXOffset() < 0 || newTable.getYOffset() < 0){
            logger.log(Level.SEVERE, "Table offset cannot be negative!");
            throw new Exception("Table offset cannot be negative!");
        }

        if (newTable.getFloor() < 0) {
            logger.log(Level.SEVERE, "Floor cannot be negative!");
            throw new Exception("Floor cannot be negative!");
        }

        if (tableRepository.existsById(newTable.getTableId())) {
            logger.log(Level.SEVERE, "Table with given Id already exist!");
            throw new Exception("Table with given Id already exist!");
        }

        // Create a new Table entity from the DTO
        Table table = new Table();
        table.setTableId(newTable.getTableId());
        table.setTableSize(newTable.getTableSize());
        table.setFloor(newTable.getFloor());
        table.setXOffset(newTable.getXOffset());
        table.setYOffset(newTable.getYOffset());

        // Set an empty list of reservations
        table.setReservations(new ArrayList<>());

        // Save the table entity
        return tableRepository.save(table);
    }

    public void updateTable(String id, TableUpdateDTO updatedTable) throws Exception {
        var table = tableRepository.getReferenceById(id);

        if (updatedTable.getTableSize() < 1){
            logger.log(Level.SEVERE, "Table size cannot be less than 1!");
            throw new Exception("Table size cannot be less than 1!");
        }

        if (updatedTable.getXOffset() < 0 || updatedTable.getYOffset() < 0) {
            logger.log(Level.SEVERE, "Table offset cannot be negative!");
            throw new Exception("Table offset cannot be negative!");
        }

        if (updatedTable.getFloor() < 0) {
            logger.log(Level.SEVERE, "Floor cannot be negative!");
            throw new Exception("Floor cannot be negative!");
        }

        if (tableRepository.existsById(id)) {
            table.setFloor(updatedTable.getFloor());
            table.setTableSize(updatedTable.getTableSize());
            table.setYOffset(updatedTable.getYOffset());
            table.setXOffset(updatedTable.getXOffset());

            tableRepository.save(table);
        }
        else {
            logger.log(Level.SEVERE, "Table does not exist!");
            throw new NoSuchElementException("Table does not exist!");
        }
    }

    public void deleteTable(String id) throws Exception {
        if (!tableRepository.existsById(id)) {
            logger.log(Level.SEVERE, "The table does not exist!");
            throw new Exception("The table does not exist!");
        }

        tableRepository.deleteById(id);
    }
}
