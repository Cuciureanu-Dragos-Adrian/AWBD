package app.restman.api.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class ReservationDTO {
    private int numberOfPeople;
    private String name;
    private OffsetDateTime dateTime;
    private String tableId;
}