package app.restman.api.controllers;

import app.restman.api.DTOs.ReservationDTO;
import app.restman.api.entities.Reservation;
import app.restman.api.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            reservationService.createReservation(newReservation);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reservation created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation != null) {
            return ResponseEntity.ok(reservation);
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
