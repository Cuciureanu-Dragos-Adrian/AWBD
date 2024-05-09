package app.restman.api.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Data
@jakarta.persistence.Table(name="table_tbl")
@NoArgsConstructor
public class Table {

    @Id
    private String tableId;
    private double xOffset;
    private double yOffset;
    private int tableSize;
    private int floor;

    @OneToMany(mappedBy = "reservedTable", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public Table(String tableId, double xOffset, double yOffset, int tableSize, int floor) {
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