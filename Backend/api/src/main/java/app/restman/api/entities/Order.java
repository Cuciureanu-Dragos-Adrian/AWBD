package app.restman.api.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@jakarta.persistence.Table(name="order_tbl")
public class Order {

    @Id
    private String orderId;

    private HashMap<String, Integer> productNamesToQuantities;
    private double price;

    @OneToOne(optional = false)
    private Table table;

    @ManyToMany
    private Set<Product> products;

    @OneToOne(mappedBy="order")
    private Payment payment;

    public Order(List<Product> products, List<Integer> quantities, Table table) throws Exception {

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

        this.table = table;
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
                ", tableId='" + table.getTableId() + '\'' +
                '}';
    }
}