package app.restman.api.DTOs;

import app.restman.api.entities.Reservation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@JsonIgnoreProperties
public class ReservationDTO {
    private String reservationId;
    private int numberOfPeople;
    private String name;
    private OffsetDateTime dateTime;
    private String tableId;

    public ReservationDTO() {}
    public ReservationDTO(Reservation reservation)
    {
        this.reservationId = reservation.getReservationId();
        this.numberOfPeople = reservation.getNumberOfPeople();
        this.name = reservation.getName();
        this.dateTime = reservation.getDateTime();
        this.tableId = reservation.getReservedTable().getTableId();
    }
}