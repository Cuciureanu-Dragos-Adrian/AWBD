package app.restman.api.services;

import app.restman.api.DTOs.ReservationDTO;
import app.restman.api.entities.Reservation;
import app.restman.api.entities.Table;
import app.restman.api.repositories.ReservationRepository;
import app.restman.api.repositories.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final TableRepository tableRepository; // Assuming you have a TableRepository

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, TableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.tableRepository = tableRepository;
    }

    public void createReservation(ReservationDTO newReservation) throws Exception {

        if (newReservation.getNumberOfPeople() < 1)
            throw new Exception("Reservation must have at least 1 person!");

        if (tableRepository.existsById(newReservation.getTableId()))
            throw new Exception("Table with given ID does not exist!");

        if (newReservation.getName().isBlank())
            throw new Exception("Name cannot be blank!");

        if (newReservation.getDateTime().isBefore(OffsetDateTime.now()))
            throw new Exception("Reservation time cannot be before the present!");

        Reservation reservation = new Reservation();
        reservation.setNumberOfPeople(newReservation.getNumberOfPeople());
        reservation.setName(newReservation.getName());
        reservation.setDateTime(newReservation.getDateTime());
        Table reservedTable = tableRepository.getReferenceById(newReservation.getTableId());
        reservation.setReservedTable(reservedTable);
        reservationRepository.save(reservation);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(String reservationId) {
        return reservationRepository.findById(reservationId).orElse(null);
    }

    public void updateReservation(String reservationId, ReservationDTO updatedReservation) throws NoSuchElementException, Exception  {
        Reservation reservation = reservationRepository.findById(reservationId).orElse(null);

        if (reservation == null)
            throw new NoSuchElementException("Reservation does not exist!");

        if (updatedReservation.getNumberOfPeople() < 1)
            throw new Exception("Reservation must have at least 1 person!");

        if (tableRepository.existsById(updatedReservation.getTableId()))
            throw new Exception("Table with given ID does not exist!");

        if (updatedReservation.getName().isBlank())
            throw new Exception("Name cannot be blank!");

        if (updatedReservation.getDateTime().isBefore(OffsetDateTime.now()))
            throw new Exception("Reservation time cannot be before the present!");

        reservation.setNumberOfPeople(updatedReservation.getNumberOfPeople());
        reservation.setName(updatedReservation.getName());
        reservation.setDateTime(updatedReservation.getDateTime());
        Table reservedTable = tableRepository.getReferenceById(updatedReservation.getTableId());
        reservation.setReservedTable(reservedTable);
        reservationRepository.save(reservation);

    }

    public void deleteReservation(String reservationId) throws Exception {
        if (!reservationRepository.existsById(reservationId))
            throw new Exception("Given reservation ID does not exist!");

        reservationRepository.deleteById(reservationId);
    }
}