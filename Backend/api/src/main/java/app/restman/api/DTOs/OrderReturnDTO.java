package app.restman.api.DTOs;

import app.restman.api.entities.Order;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderReturnDTO {
    private String orderId;
    private String tableId;
    private double price;
    private List<ProductDTO> products;
    private List<Integer> quantities;

    public OrderReturnDTO() { }

    public OrderReturnDTO(Order order){
        this.orderId = order.getOrderId();
        this.tableId = order.getTable().getTableId();
        this.price = order.getPrice();
        this.products = new ArrayList<>();
        this.quantities = new ArrayList<>();

        var products = order.getProducts();
        var quantities = order.getProductNamesToQuantities();

        for (var product : products){
            this.products.add(new ProductDTO(product));
            this.quantities.add(quantities.get(product.getName()));
        }
    }
}