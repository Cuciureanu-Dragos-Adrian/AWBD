package app.restman.api.services;

import app.restman.api.DTOs.ReservationDTO;
import app.restman.api.entities.Reservation;
import app.restman.api.entities.Table;
import app.restman.api.repositories.ReservationRepository;
import app.restman.api.repositories.TableRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.data.domain.Sort;

@Service
@AllArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository; // Assuming you have a TableRepository

    @Getter
    private final int reservationDuration = 3;
    //TODO - remove hardcoded duration

    private static final Logger logger = Logger.getLogger(ReservationService.class.getName());

    // Helper method to check for overlapping date times
    private boolean isDateTimeOverlapping(OffsetDateTime dateTime1, OffsetDateTime dateTime2) {
        return dateTime1.isBefore(dateTime2.plusHours(reservationDuration)) && dateTime2.isBefore(dateTime1.plusHours(reservationDuration));
    }

    public Reservation createReservation(ReservationDTO newReservation) throws Exception {

        if (newReservation.getNumberOfPeople() < 1) {
            logger.log(Level.SEVERE, "Reservation must have at least 1 person!");
            throw new Exception("Reservation must have at least 1 person!");
        }

        if (!tableRepository.existsById(newReservation.getTableId())) {
            logger.log(Level.SEVERE, "Table with given ID does not exist!");
            throw new Exception("Table with given ID does not exist!");
        }

        if (newReservation.getName().isBlank()) {
            logger.log(Level.SEVERE, "Name cannot be blank!");
            throw new Exception("Name cannot be blank!");
        }

        if (newReservation.getDateTime().isBefore(OffsetDateTime.now())) {
            logger.log(Level.SEVERE, "Reservation time cannot be before the present!");
            throw new Exception("Reservation time cannot be before the present!");
        }

        // Check for overlapping reservations
        List<Reservation> allReservations = new LinkedList<>();
        reservationRepository.findAll(Sort.by("dateTime")).iterator().forEachRemaining(allReservations::add);

        for (Reservation existingReservation : allReservations) {
            if (existingReservation.getReservedTable().getTableId().equals(newReservation.getTableId()) &&
                    isDateTimeOverlapping(existingReservation.getDateTime(), newReservation.getDateTime())) {
                logger.log(Level.SEVERE, "This table is already booked at this time!");
                throw new Exception("This table is already booked at this time!");
            }
        }

        Reservation reservation = new Reservation();
        reservation.setReservationId(UUID.randomUUID().toString());;
        reservation.setNumberOfPeople(newReservation.getNumberOfPeople());
        reservation.setName(newReservation.getName());
        reservation.setDateTime(newReservation.getDateTime());
        Table reservedTable = tableRepository.getReferenceById(newReservation.getTableId());
        reservation.setReservedTable(reservedTable);
        return reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservationsAsc() {
        List<Reservation> allReservations = new LinkedList<>();
        reservationRepository.findAll(Sort.by("dateTime")).iterator().forEachRemaining(allReservations::add);
        return allReservations;
    }

    public List<Reservation> getAllReservationsDesc() {
        List<Reservation> allReservations = new LinkedList<>();
        reservationRepository.findAll(Sort.by(Sort.Direction.DESC,"dateTime")).iterator().forEachRemaining(allReservations::add);
        return allReservations;
    }

    public Reservation getReservationById(String reservationId) {
        return reservationRepository.findByReservationId(reservationId).orElse(null);
    }

    public Reservation getOngoingReservationByTableId(String tableId){
        return getAllReservationsAsc()
                .stream()
                .filter(reservation -> reservation.getReservedTable().getTableId().equals(tableId) && isCurrentReservation(reservation))
                .findFirst()
                .orElse(null);
    }

    public Page<Reservation> getAllReservationsPageAsc(Pageable page) {
        return reservationRepository.findAll(page);
    }

    public Page<Reservation> getAllReservationsPageDesc(Pageable page) {
        return reservationRepository.findAll(page);
    }

    public void updateReservation(String reservationId, ReservationDTO updatedReservation) throws NoSuchElementException, Exception  {
        Reservation reservation = reservationRepository.findByReservationId(reservationId).orElse(null);

        if (reservation == null) {
            logger.log(Level.SEVERE, "Reservation does not exist!");
            throw new NoSuchElementException("Reservation does not exist!");
        }

        if (updatedReservation.getNumberOfPeople() < 1) {
            logger.log(Level.SEVERE, "Reservation must have at least 1 person!");
            throw new Exception("Reservation must have at least 1 person!");
        }

        if (!tableRepository.existsById(updatedReservation.getTableId())) {
            logger.log(Level.SEVERE, "Table with given ID does not exist!");
            throw new Exception("Table with given ID does not exist!");
        }

        if (updatedReservation.getName().isBlank()) {
            logger.log(Level.SEVERE, "Name cannot be blank!");
            throw new Exception("Name cannot be blank!");
        }

        if (updatedReservation.getDateTime().isBefore(OffsetDateTime.now())) {
            logger.log(Level.SEVERE, "Reservation time cannot be before the present!");
            throw new Exception("Reservation time cannot be before the present!");
        }

        // Check for overlapping reservations
        List<Reservation> allReservations = new LinkedList<>();
        reservationRepository.findAll(Sort.by("dateTime")).iterator().forEachRemaining(allReservations::add);

        for (Reservation existingReservation : allReservations) {
            if (existingReservation.getReservedTable().getTableId().equals(updatedReservation.getTableId()) &&
                    isDateTimeOverlapping(existingReservation.getDateTime(), updatedReservation.getDateTime())) {
                logger.log(Level.SEVERE, "This table is already booked at this time!");
                throw new Exception("This table is already booked at this time!");
            }
        }

        reservation.setNumberOfPeople(updatedReservation.getNumberOfPeople());
        reservation.setName(updatedReservation.getName());
        reservation.setDateTime(updatedReservation.getDateTime());
        Table reservedTable = tableRepository.getReferenceById(updatedReservation.getTableId());
        reservation.setReservedTable(reservedTable);
        reservationRepository.save(reservation);
    }

    public static boolean isCurrentReservation(Reservation reservation) {
        OffsetDateTime now = OffsetDateTime.now();

        // Check if reservation starts within the next 3 hours
        return (reservation.getDateTime().isAfter(now) && reservation.getDateTime().isBefore(now.plusHours(3))) ||
                (reservation.getDateTime().isBefore(now) && reservation.getDateTime().plusHours(3).isAfter(now));
    }

    public void deleteReservation(String reservationId) throws Exception {
        if (!reservationRepository.existsByReservationId(reservationId)) {
            logger.log(Level.SEVERE, "Given reservation ID does not exist!");
            throw new Exception("Given reservation ID does not exist!");
        }

        reservationRepository.deleteByReservationId(reservationId);
    }
}
