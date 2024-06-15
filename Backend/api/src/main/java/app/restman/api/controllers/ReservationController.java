package app.restman.api.controllers;

import app.restman.api.DTOs.OrderReturnDTO;
import app.restman.api.DTOs.ReservationDTO;
import app.restman.api.entities.Order;
import app.restman.api.entities.Reservation;
import app.restman.api.services.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("reservations")
@CrossOrigin(origins = "*")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Create a new reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Reservation created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<String> createReservation(@RequestBody ReservationDTO newReservation) {
        try {
            Reservation createdReservation = reservationService.createReservation(newReservation);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation.getReservationId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Get all reservations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @GetMapping("/getAll")
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservationsAsc();
        List<ReservationDTO> reservationDTOs = reservations.stream()
                .map(ReservationDTO::new) // Convert each Reservation to ReservationDTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTOs);
    }

    @Operation(summary = "Get current reservation by table ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reservation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = @Content)})
    @GetMapping("/getCurrentByTableId/{tableId}")
    public ResponseEntity<?> getCurrentReservationByTableId(@PathVariable String tableId){
        var reservation = reservationService.getOngoingReservationByTableId(tableId);
        if (reservation != null) {
            return ResponseEntity.ok(new ReservationDTO(reservation)); // Use your OrderDTO constructor
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Helper method to check reservation expiration
    private boolean isReservationNotExpired(Reservation reservation) {
        OffsetDateTime endTime = reservation.getDateTime().plusHours(reservationService.getReservationDuration());
        return endTime.isAfter(OffsetDateTime.now());
    }

    @Operation(summary = "Get all not expired reservations in ascending order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @GetMapping("/getAllNotExpiredAsc")
    public ResponseEntity<List<ReservationDTO>> getAllNotExpiredReservationsAsc() {
        List<Reservation> reservations = reservationService.getAllReservationsAsc();

        // Filter reservations based on expiration criteria
        List<Reservation> notExpiredReservations = reservations.stream()
                .filter(this::isReservationNotExpired)
                .toList();

        List<ReservationDTO> reservationDTOs = notExpiredReservations.stream()
                .map(ReservationDTO::new) // Convert each Reservation to ReservationDTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTOs);
    }

    @Operation(summary = "Get all not expired reservations in descending order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @GetMapping("/getAllNotExpiredDesc")
    public ResponseEntity<List<ReservationDTO>> getAllNotExpiredReservationsDesc() {
        List<Reservation> reservations = reservationService.getAllReservationsDesc();

        // Filter reservations based on expiration criteria
        List<Reservation> notExpiredReservations = reservations.stream()
                .filter(this::isReservationNotExpired)
                .toList();

        List<ReservationDTO> reservationDTOs = notExpiredReservations.stream()
                .map(ReservationDTO::new) // Convert each Reservation to ReservationDTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(reservationDTOs);
    }

    @Operation(summary = "Get all not expired reservations in ascending order with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @GetMapping("/getAllNotExpiredPageAsc")
    public ResponseEntity<Page<ReservationDTO>> getAllNotExpiredReservationsAsc(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "dateTime") String sortBy) {
        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sortBy).ascending());
        Page<Reservation> notExpiredReservations = reservationService.getAllReservationsPageAsc(pageable);

        List<ReservationDTO> reservationDTOs = notExpiredReservations.getContent().stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(reservationDTOs, pageable, notExpiredReservations.getTotalElements()));
    }

    @Operation(summary = "Get all not expired reservations in descending order with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @GetMapping("/getAllNotExpiredPageDesc")
    public ResponseEntity<Page<ReservationDTO>> getAllNotExpiredReservationsDesc(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "dateTime") String sortBy) {
        Pageable pageable = PageRequest.of(page - 1, 15, Sort.by(sortBy).descending());
        Page<Reservation> notExpiredReservations = reservationService.getAllReservationsPageDesc(pageable);

        List<ReservationDTO> reservationDTOs = notExpiredReservations.getContent().stream()
                .map(ReservationDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(reservationDTOs, pageable, notExpiredReservations.getTotalElements()));
    }

    @Operation(summary = "Get reservation by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved reservation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ReservationDTO.class))),
            @ApiResponse(responseCode = "404", description = "Reservation not found",
                    content = @Content)})
    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable String reservationId) {
        Reservation reservation = reservationService.getReservationById(reservationId);
        if (reservation != null) {
            return ResponseEntity.ok(new ReservationDTO(reservation)); // Convert to ReservationDTO
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Update an existing reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation updated successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @PutMapping("/{reservationId}")
    public ResponseEntity<String> updateReservation(@PathVariable String reservationId, @RequestBody ReservationDTO updatedReservation) {
        try {
            reservationService.updateReservation(reservationId, updatedReservation);
            return ResponseEntity.ok("Reservation updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Delete an existing reservation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservation deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
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
