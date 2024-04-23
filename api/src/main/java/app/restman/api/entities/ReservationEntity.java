package app.restman.api.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Setter
@Getter
public class ReservationEntity {

    @Id
    private int reservationId;
    private int numberOfPeople;
    private String name;
    private LocalDateTime dateTime;
    private String tableId;

    public ReservationEntity() { }

    public ReservationEntity(int numberOfPeople, String name, LocalDateTime dateTime, String tableId) {
        this.numberOfPeople = numberOfPeople;
        this.name = name;
        this.dateTime = dateTime;
        this.tableId = tableId;

        //TODO - ID

    }
}