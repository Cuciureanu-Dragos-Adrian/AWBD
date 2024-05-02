package app.restman.api.domain;

import app.restman.api.entities.Table;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@ActiveProfiles("h2")
public class EntityManagerTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void findTable() {
        System.out.println(entityManager.getEntityManagerFactory());
        Table tableFound = entityManager.find(Table.class, "A1");

        Assertions.assertEquals(tableFound.getFloor(), 0);
        Assertions.assertEquals(tableFound.getXOffset(), 100);
        Assertions.assertEquals(tableFound.getYOffset(), 100);
    }
}