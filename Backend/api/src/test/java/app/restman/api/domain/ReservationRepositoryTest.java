package app.restman.api.domain;

import app.restman.api.entities.Reservation;
import app.restman.api.repositories.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;

@DataJpaTest
@ActiveProfiles("h2")
@Slf4j
public class ReservationRepositoryTest {

    ReservationRepository reservationRepository;

    @Autowired
    ReservationRepositoryTest(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Test
    public void findByReservationId() {
        Optional<Reservation> reservation = reservationRepository.findByReservationId("reservation0");
        assertFalse(reservation.isEmpty());
        log.info("findByReservationId ...");
        log.info(reservation.get().getReservationId());
    }

    @Test
    public void existsByReservationId() {
        boolean result = reservationRepository.existsByReservationId("reservation0");
        Assertions.assertTrue(result);
        log.info("existsByReservationId ...");
        log.info(String.valueOf(result));
    }
}