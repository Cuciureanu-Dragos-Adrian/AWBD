package app.restman.api.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class MenuCategoryEntity {

    @Id
    private String name;
    private String iconBase64;

    public MenuCategoryEntity() {
    }

    public MenuCategoryEntity(String name, String iconBase64) {
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
