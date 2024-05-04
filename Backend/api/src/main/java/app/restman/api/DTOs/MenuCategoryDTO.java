package app.restman.api.DTOs;

import app.restman.api.entities.MenuCategory;
import app.restman.api.services.MenuCategoryService;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuCategoryDTO {
    private String name;
    private String iconBase64;

    public MenuCategoryDTO() { }

    public MenuCategoryDTO(MenuCategory category)
    {
        this.name = category.getName();
        this.iconBase64 = category.getIconBase64();
    }
}