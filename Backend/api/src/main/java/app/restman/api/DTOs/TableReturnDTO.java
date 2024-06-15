package app.restman.api.DTOs;

import app.restman.api.entities.Table;
import app.restman.api.services.ReservationService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;


@Getter
@Setter
public class TableReturnDTO extends RepresentationModel<TableReturnDTO> {

    private String tableId;
    private double xOffset;
    private double yOffset;
    private int tableSize;
    private int floor;
    private boolean hasOrder;
    private boolean hasReservation;

    public TableReturnDTO() { }

    public TableReturnDTO(Table table) {
        this.tableId = table.getTableId();
        this.xOffset = table.getXOffset();
        this.yOffset = table.getYOffset();
        this.tableSize = table.getTableSize();
        this.floor = table.getFloor();
        this.hasOrder = table.getOrder() != null;
        this.hasReservation = !table.getReservations().stream()
                .filter(ReservationService::isCurrentReservation)
                .toList().isEmpty();
    }
}
