package app.restman.api.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private String tableId;
    private List<String> productNames;
    private List<Integer> quantities;
}