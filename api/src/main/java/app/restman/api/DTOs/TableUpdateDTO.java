package app.restman.api.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TableUpdateDTO
{
    private int tableSize;
    private int floor;
    private double xOffset;
    private double yOffset;
}
