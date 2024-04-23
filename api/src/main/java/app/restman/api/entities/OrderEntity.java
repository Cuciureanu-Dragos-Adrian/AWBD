package app.restman.api.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class OrderEntity {

    @Id
    private String orderId;
    private List<ProductEntity> products;
    private List<Integer> quantities;
    private double price;
    private String tableId;

    public OrderEntity() {
    }

    public OrderEntity(List<ProductEntity> products, List<Integer> quantities, String tableId) {
        this.products = products;
        this.quantities = quantities;
        this.tableId = tableId;
        calculatePrice();

        //TODO - ID
    }

    private void calculatePrice() {
        price = 0;
        for (int index = 0; index < products.size(); index++) {
            price += products.get(index).getPrice() * quantities.get(index);
        }
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "products=" + products +
                ", quantities=" + quantities +
                ", price=" + price +
                ", tableId='" + tableId + '\'' +
                '}';
    }
}