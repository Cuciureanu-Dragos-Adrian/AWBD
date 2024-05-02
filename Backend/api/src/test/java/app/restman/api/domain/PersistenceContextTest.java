package app.restman.api.domain;

import app.restman.api.entities.Table;
import app.restman.api.services.PersistenceContextExtended;
import app.restman.api.services.PersistenceContextTransaction;
import jakarta.persistence.TransactionRequiredException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = app.restman.api.ApiApplication.class)
@ActiveProfiles("h2")
@ExtendWith(SpringExtension.class)
public class PersistenceContextTest {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceContextTest.class);

    @Autowired
    PersistenceContextExtended persistenceContextExtended;

    @Autowired
    PersistenceContextTransaction persistenceContextTransaction;

    @Test
    public void persistenceContextTransactionThrowException() {
        logger.info("Testing persistenceContextTransactionThrowException...");
        assertThrows(TransactionRequiredException.class, () -> {
            persistenceContextTransaction.update("A1", 200, 200);
        });
        logger.info("Test persistenceContextTransactionThrowException completed.");
    }

    @Test
    public void persistenceContextTransactionExtended() {
        logger.info("Testing persistenceContextTransactionExtended...");
        persistenceContextTransaction.updateInTransaction("A1", 200, 200);
        Table tableExtended = persistenceContextExtended.find("A1");

        logger.info("X Offset: {}", tableExtended.getXOffset());
        logger.info("Y Offset: {}", tableExtended.getYOffset());

        Assertions.assertEquals(tableExtended.getXOffset(), 200);
        Assertions.assertEquals(tableExtended.getYOffset(), 200);
        logger.info("Test persistenceContextTransactionExtended completed.");
    }

    @Test
    public void persistenceContextExtendedExtended() {
        logger.info("Testing persistenceContextExtendedExtended...");
        persistenceContextExtended.update("A1", 200, 200);
        Table tableExtended = persistenceContextExtended.find("A1");

        logger.info("X Offset: {}", tableExtended.getXOffset());
        logger.info("Y Offset: {}", tableExtended.getYOffset());

        Assertions.assertEquals(tableExtended.getXOffset(), 200);
        Assertions.assertEquals(tableExtended.getYOffset(), 200);
        logger.info("Test persistenceContextExtendedExtended completed.");
    }

    @Test
    public void persistenceContextExtendedTransaction() {
        logger.info("Testing persistenceContextExtendedTransaction...");
        persistenceContextExtended.update("A1", 200, 200);
        Table tableExtended = persistenceContextTransaction.find("A1");

        logger.info("X Offset: {}", tableExtended.getXOffset());
        logger.info("Y Offset: {}", tableExtended.getYOffset());

        Assertions.assertEquals(tableExtended.getXOffset(), 200);
        Assertions.assertEquals(tableExtended.getYOffset(), 200);
        logger.info("Test persistenceContextExtendedTransaction completed.");
    }
}
