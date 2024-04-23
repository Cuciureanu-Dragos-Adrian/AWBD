package app.restman.api.controllers;

import app.restman.api.entities.ReservationEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    private static List<ReservationEntity> reservations = new ArrayList<>();

    @PostMapping("/add")
    public void createReservation(@RequestBody ReservationEntity reservation) {
        reservations.add(reservation);
    }

    @GetMapping("/getAll")
    public List<ReservationEntity> getAllReservations() {

        //TODO - fetch from somewhere
        return reservations;
    }

    @GetMapping("/{id}")
    public ReservationEntity getReservationById(@PathVariable int id) {
        //TODO - fetch from somewhere

        for (ReservationEntity reservation : reservations) {
            if (reservation.getReservationId() == id) {
                return reservation;
            }
        }
        return null; // Or throw an exception if reservation not found
    }

    @PutMapping("/{id}")
    public void updateReservation(@PathVariable int id, @RequestBody ReservationEntity updatedReservation) {
        for (int index = 0; index < reservations.size(); index++) {
            ReservationEntity reservation = reservations.get(index);
            if (reservation.getReservationId() == id) {
                reservations.set(index, updatedReservation);
                //TODO - fetch from somewhere
                return;
            }
        }
        //TODO - handle error

        // If reservation with the given ID is not found, you can throw an exception or handle it as needed
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable int id) {
        reservations.removeIf(reservation -> reservation.getReservationId() == id);
        //TODO - save changes
    }
}
