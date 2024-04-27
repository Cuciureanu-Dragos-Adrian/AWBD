package app.restman.api.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDTO {
    private String name;
    private double price;
    private String categoryName;
}