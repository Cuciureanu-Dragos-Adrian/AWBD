package app.restman.api.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class MenuCategory {

    @Id
    private String name;
    private String iconBase64;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Product> products;

    public MenuCategory(String name, String iconBase64) {
        this.name = name;
        this.iconBase64 = iconBase64;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"iconBase64\": \"" + iconBase64 + "\"\n" +
                "}";
    }
}
