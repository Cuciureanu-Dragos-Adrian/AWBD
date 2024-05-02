package app.restman.api.services;

import app.restman.api.entities.Table;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class PersistenceContextTransaction {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Table updateInTransaction(String tableId, double x, double y) {
        Table updatedTable = entityManager.find(Table.class, tableId);
        updatedTable.setXOffset(x);
        updatedTable.setYOffset(y);

        entityManager.persist(updatedTable);

        return updatedTable;
    }

    public Table update(String tableId, double x, double y) {
        Table updatedTable = entityManager.find(Table.class, tableId);
        updatedTable.setXOffset(x);
        updatedTable.setYOffset(y);

        entityManager.persist(updatedTable);

        return updatedTable;
    }

    public Table find(String id) {
        return entityManager.find(Table.class, id);
    }
}