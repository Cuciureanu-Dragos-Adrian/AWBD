package app.restman.api.controllers;

import app.restman.api.entities.MenuCategoryEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/menu/categories")
public class MenuCategoryController {

    private static List<MenuCategoryEntity> menuCategories = new ArrayList<>();

    @PostMapping("/add")
    public void createMenuCategory(@RequestBody MenuCategoryEntity menuCategory) {
        menuCategories.add(menuCategory);
    }

    @GetMapping("/getAll")
    public List<MenuCategoryEntity> getAllMenuCategories() {
        return menuCategories;
    }

    @GetMapping("/{name}")
    public MenuCategoryEntity getMenuCategoryByName(@PathVariable String name) {
        for (MenuCategoryEntity category : menuCategories) {
            if (category.getName().equals(name)) {
                return category;
            }
        }
        return null; // Or throw an exception if category not found
    }

    @PutMapping("/{name}")
    public void updateMenuCategory(@PathVariable String name, @RequestBody MenuCategoryEntity updatedCategory) {
        for (int i = 0; i < menuCategories.size(); i++) {
            MenuCategoryEntity category = menuCategories.get(i);
            if (category.getName().equals(name)) {
                menuCategories.set(i, updatedCategory);
                return;
            }
        }
        // If category with the given name is not found, you can throw an exception or handle it as needed
    }

    @DeleteMapping("/{name}")
    public void deleteMenuCategory(@PathVariable String name) {
        menuCategories.removeIf(category -> category.getName().equals(name));
    }
}