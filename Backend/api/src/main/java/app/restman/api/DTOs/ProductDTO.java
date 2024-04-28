package app.restman.api.DTOs;

import app.restman.api.entities.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private double price;
    private String categoryName;

    public ProductDTO() { }

    public ProductDTO(Product prod) {
        this.name = prod.getName();
        this.price = prod.getPrice();
        this.categoryName = prod.getProdCategory();
    }
}