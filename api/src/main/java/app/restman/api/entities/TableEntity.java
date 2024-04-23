package app.restman.api.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Setter
@Getter
public class TableEntity {

    @Id
    private String tableId;
    private double xOffset;
    private double yOffset;
    private int tableSize;
    private int floor;

    public TableEntity() { }

    public TableEntity(String tableId, double xOffset, double yOffset, int tableSize, int floor) {
        this.tableId = tableId;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.tableSize = tableSize;
        this.floor = floor;
    }

    @Override
    public String toString() {
        return "TableModel{" +
                ", tableId='" + tableId + '\'' +
                ", xOffset=" + xOffset +
                ", yOffset=" + yOffset +
                ", tableSize=" + tableSize +
                ", floor=" + floor +
                '}';
    }
}