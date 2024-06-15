package app.restman.api.controllers;

import app.restman.api.DTOs.MenuCategoryDTO;
import app.restman.api.entities.MenuCategory;
import app.restman.api.services.MenuCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("menu_categories")
@CrossOrigin(origins = "*")
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    @Autowired
    public MenuCategoryController(MenuCategoryService menuCategoryService) {
        this.menuCategoryService = menuCategoryService;
    }

    @Operation(summary = "Get all menu categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MenuCategoryDTO.class)))})
    @GetMapping("/getAll")
    public ResponseEntity<List<MenuCategoryDTO>> getAllCategories() {
        List<MenuCategory> categories = menuCategoryService.getAllCategories();
        List<MenuCategoryDTO> categoryDTOs = categories.stream()
                .map(MenuCategoryDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(categoryDTOs);
    }

    @Operation(summary = "Create a new menu category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<String> createCategory(@RequestBody MenuCategoryDTO categoryDTO) {
        try {
            MenuCategory category = menuCategoryService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category created successfully with name: " + category.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Update an existing menu category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category updated successfully",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content)})
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

    @Operation(summary = "Delete an existing menu category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category deleted successfully",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Category not found",
                    content = @Content)})
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