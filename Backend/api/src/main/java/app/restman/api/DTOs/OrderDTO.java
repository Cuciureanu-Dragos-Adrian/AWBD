package app.restman.api.DTOs;

import app.restman.api.entities.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderDTO {
    private String orderId;
    private String tableId;
    private double price;
    private List<String> productNames;
    private List<Integer> quantities;
}