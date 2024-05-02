package app.restman.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Entity
@Data
public class Reservation {

    @Id
    private String reservationId;
    private int numberOfPeople;
    private String name;
    private OffsetDateTime dateTime;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "tableId")
    private Table reservedTable;

    public Reservation() { }

    public Reservation(int numberOfPeople, String name, OffsetDateTime dateTime, Table reservedTable) {
        this.numberOfPeople = numberOfPeople;
        this.name = name;
        this.dateTime = dateTime;
        this.reservedTable = reservedTable;
    }
}