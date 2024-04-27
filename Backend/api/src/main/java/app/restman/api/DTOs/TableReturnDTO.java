package app.restman.api.DTOs;

import app.restman.api.entities.Reservation;
import app.restman.api.entities.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TableReturnDTO {

    private String tableId;
    private double xOffset;
    private double yOffset;
    private int tableSize;
    private int floor;
    private List<ReservationDTO> reservations;

    public TableReturnDTO() { }

    public TableReturnDTO(Table table) {
        this.tableId = table.getTableId();
        this.xOffset = table.getXOffset();
        this.yOffset = table.getYOffset();
        this.tableSize = table.getTableSize();
        this.floor = table.getFloor();
        this.reservations = table.getReservations().stream()
                .map(ReservationDTO::new) // Convert each Reservation to ReservationDTO
                .collect(Collectors.toList());
    }
}
