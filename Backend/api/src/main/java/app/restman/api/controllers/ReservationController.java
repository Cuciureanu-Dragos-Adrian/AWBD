package app.restman.api.controllers;

import app.restman.api.DTOs.ReservationDTO;
import app.restman.api.entities.Reservation;
import app.restman.api.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody ReservationDTO newReservation) {
        try {
            Reservation createdReservation = reservationService.createReservation(newReservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation.getReservationId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        List<ReservationDTO> reservationDTOs = reservations.stream()
                .map(ReservationDTO::new) // Convert each Reservation to ReservationDTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTOs);
    }

    // Helper method to check reservation expiration
    private boolean isReservationNotExpired(Reservation reservation) {
        OffsetDateTime endTime = reservation.getDateTime().plusHours(reservationService.getReservationDuration());
        return endTime.isAfter(OffsetDateTime.now());
    }

    @GetMapping("/getAllNotExpired")
    public ResponseEntity<List<ReservationDTO>> getAllNotExpiredReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();

        // Filter reservations based on expiration criteria
        List<Reservation> notExpiredReservations = reservations.stream()
                .filter(this::isReservationNotExpired)
                .toList();

        List<ReservationDTO> reservationDTOs = notExpiredReservations.stream()
                .map(ReservationDTO::new) // Convert each Reservation to ReservationDTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTOs);
    }



    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable String reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation != null) {
            return ResponseEntity.ok(new ReservationDTO(reservation)); // Convert to ReservationDTO
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{reservationId}")
    public ResponseEntity<String> updateReservation(@PathVariable String reservationId, @RequestBody ReservationDTO updatedReservation) {
        try {
            reservationService.updateReservation(reservationId, updatedReservation);
            return ResponseEntity.ok("Reservation updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@PathVariable String reservationId) {
        try {
            reservationService.deleteReservation(reservationId);
            return ResponseEntity.ok("Reservation deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
