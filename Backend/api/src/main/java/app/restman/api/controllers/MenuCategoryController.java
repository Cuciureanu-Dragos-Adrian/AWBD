package app.restman.api.controllers;

import app.restman.api.DTOs.MenuCategoryDTO;
import app.restman.api.entities.MenuCategory;
import app.restman.api.services.MenuCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("menu_categories")
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    @Autowired
    public MenuCategoryController(MenuCategoryService menuCategoryService) {
        this.menuCategoryService = menuCategoryService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<MenuCategoryDTO>> getAllCategories() {
        List<MenuCategory> categories = menuCategoryService.getAllCategories();
        List<MenuCategoryDTO> categoryDTOs = categories.stream()
                .map(MenuCategoryDTO::new) // Convert each MenuCategory to MenuCategoryDTO
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody MenuCategoryDTO categoryDTO) {
        try {
            MenuCategory category = menuCategoryService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully with name: " + category.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{name}")
    public ResponseEntity<String> updateCategory(@PathVariable String name, @RequestBody MenuCategoryDTO updatedCategoryDTO) {
        try {
            menuCategoryService.updateCategory(name, updatedCategoryDTO);
            return ResponseEntity.ok("Category updated successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> deleteCategory(@PathVariable String name) {
        try {
            menuCategoryService.deleteCategory(name);
            return ResponseEntity.ok("Category deleted successfully");
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}