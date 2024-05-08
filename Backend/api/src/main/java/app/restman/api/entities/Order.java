package app.restman.api.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

import java.util.*;

@Entity
@Setter
@Getter
@Table(name="order_tbl")
public class Order {

    @Id
    private String orderId;

    private HashMap<String, Integer> productNamesToQuantities;
    private double price;
    private String tableId;

    @ManyToMany
    private Set<Product> products;

    @OneToOne(mappedBy="order")
    @Nullable
    private Payment payment;

    public Order() {
    }

    public Order(List<Product> products, List<Integer> quantities, String tableId) throws Exception {

        if (products.size() != quantities.size())
            throw new Exception(("Product size list must match length of  quantities list!"));

        this.productNamesToQuantities = new HashMap<>();
        this.products = new HashSet<>();

        for (int index = 0; index < products.size(); index++)
        {
            var product = products.get(index);
            var quantity = quantities.get(index);

            this.products.add(product);
            this.productNamesToQuantities.put(product.getName(), quantity);
        }

        this.tableId = tableId;
        this.orderId = UUID.randomUUID().toString();
        calculatePrice();
    }

    public void calculatePrice() {
        price = 0;
        for(var product : this.products)
        {
            price += product.getPrice() * productNamesToQuantities.get(product.getName());
        }
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "products=" + products +
                ", price=" + price +
                ", tableId='" + tableId + '\'' +
                '}';
    }
}