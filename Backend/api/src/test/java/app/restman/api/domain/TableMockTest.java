package app.restman.api.domain;

import app.restman.api.DTOs.TableCreateDTO;
import app.restman.api.DTOs.TableUpdateDTO;
import app.restman.api.entities.Table;
import app.restman.api.repositories.TableRepository;
import app.restman.api.services.TableService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.*;

public class TableMockTest {

    private static final Logger logger = Logger.getLogger(TableMockTest.class.getName());

    @Mock
    private TableRepository tableRepository;

    @InjectMocks
    private TableService tableService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createTable() throws Exception {
        logger.info("Testing createTable...");

        TableCreateDTO newTableDTO = new TableCreateDTO();
        newTableDTO.setTableId("C1");
        newTableDTO.setTableSize(4);
        newTableDTO.setFloor(1);
        newTableDTO.setXOffset(100);
        newTableDTO.setYOffset(100);

        Table table = new Table();
        table.setTableId("C1");
        table.setTableSize(4);
        table.setFloor(1);
        table.setXOffset(100);
        table.setYOffset(100);
        table.setReservations(new ArrayList<>());

        when(tableRepository.existsById("C1")).thenReturn(false);
        when(tableRepository.save(any(Table.class))).thenReturn(table);

        Table result = tableService.createTable(newTableDTO);

        Assertions.assertEquals(table, result);

        logger.info("createTable test passed.");
    }

    @Test
    public void getAllTables() {
        logger.info("Testing getAllTables...");

        List<Table> tables = new ArrayList<>();
        when(tableRepository.findAll()).thenReturn(tables);

        List<Table> result = tableService.getAllTables();

        Assertions.assertEquals(tables, result);

        logger.info("getAllTables test passed.");
    }

    @Test
    public void updateTable() throws Exception {
        logger.info("Testing updateTable...");

        String id = "C1";
        TableUpdateDTO updatedTableDTO = new TableUpdateDTO();
        updatedTableDTO.setTableSize(4);
        updatedTableDTO.setFloor(1);
        updatedTableDTO.setXOffset(100);
        updatedTableDTO.setYOffset(100);

        Table table = new Table();
        table.setTableSize(4);
        table.setFloor(1);
        table.setXOffset(100);
        table.setYOffset(100);

        when(tableRepository.existsById(id)).thenReturn(true);
        when(tableRepository.getReferenceById(id)).thenReturn(table);

        tableService.updateTable(id, updatedTableDTO);

        verify(tableRepository, times(1)).save(table);

        logger.info("updateTable test passed.");
    }

    @Test
    public void deleteTable() throws Exception {
        logger.info("Testing deleteTable...");

        String id = "A1";

        when(tableRepository.existsById(id)).thenReturn(true);

        tableService.deleteTable(id);

        verify(tableRepository, times(1)).deleteById(id);

        logger.info("deleteTable test passed.");
    }
}
