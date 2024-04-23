package app.restman.api.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class ProductEntity {

    @Id
    private String name;
    private double price;
    private String category;

    public ProductEntity() { }

    public ProductEntity(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }
}
