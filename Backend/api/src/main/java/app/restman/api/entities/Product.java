package app.restman.api.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
public class Product {

    @Id
    private String name;
    private double price;
    //TODO - remove temp prop
    private String prodCategory;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "categoryName")
    private MenuCategory category;

    @ManyToMany(mappedBy = "products")
    private Set<Order> orders;

    public Product() { }

    public Product(String name, double price, MenuCategory category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
