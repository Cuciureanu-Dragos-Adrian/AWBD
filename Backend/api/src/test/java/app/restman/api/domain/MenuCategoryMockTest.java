package app.restman.api.domain;

import app.restman.api.DTOs.MenuCategoryDTO;
import app.restman.api.entities.MenuCategory;
import app.restman.api.entities.Table;
import app.restman.api.repositories.MenuCategoryRepository;
import app.restman.api.services.MenuCategoryService;
import app.restman.api.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuCategoryMockTest {

    @Mock
    private MenuCategoryRepository menuCategoryRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private MenuCategoryService menuCategoryService;

    private static final Logger logger = Logger.getLogger(MenuCategoryMockTest.class.getName());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAllCategories() {
        // Mock data
        List<MenuCategory> mockCategories = new ArrayList<>();
        MenuCategory category1 = new MenuCategory();
        category1.setName("Category1");
        MenuCategory category2 = new MenuCategory();
        category2.setName("Category2");
        mockCategories.add(category1);
        mockCategories.add(category2);

        when(menuCategoryRepository.findAll()).thenReturn(mockCategories);

        // Call service method
        List<MenuCategory> categories = menuCategoryService.getAllCategories();

        // Verify that all categories are returned and sorted
        assertEquals(2, categories.size());
        assertEquals("Category1", categories.get(0).getName());
        assertEquals("Category2", categories.get(1).getName());

        logger.info("getAllCategories test passed.");
    }

    @Test
    void getCategoryByName() {
        // Mock data
        MenuCategory mockCategory = new MenuCategory();
        mockCategory.setName("Category1");

        when(menuCategoryRepository.findById("Category1")).thenReturn(java.util.Optional.of(mockCategory));

        // Call service method
        MenuCategory category = menuCategoryService.getCategoryByName("Category1");

        // Verify that the correct category is returned
        assertNotNull(category);
        assertEquals("Category1", category.getName());

        logger.info("getCategoryByName test passed.");
    }

    @Test
    void createCategoryError() throws Exception {
        // Mock data
        MenuCategoryDTO categoryDTO = new MenuCategoryDTO();
        categoryDTO.setName("Category1");

        MenuCategory category = new MenuCategory();
        category.setName("Category1");

        // Mock repository behavior to simulate category with the same name already exists
        when(menuCategoryRepository.existsById("Category1")).thenReturn(false);
        when(menuCategoryRepository.save(any(MenuCategory.class))).thenReturn(category);

        MenuCategory result = menuCategoryService.createCategory(categoryDTO);

        // Verify that the service method throws an exception
        Assertions.assertEquals(category, result);

        logger.info("createCategory test passed.");
    }


    @Test
    void updateCategory() throws Exception {
        // Mock data
        MenuCategoryDTO updatedCategoryDTO = new MenuCategoryDTO();
        updatedCategoryDTO.setName("Category2");

        MenuCategory existingCategory = new MenuCategory();
        existingCategory.setName("Category1");

        when(menuCategoryRepository.findById("Category1")).thenReturn(java.util.Optional.of(existingCategory));

        // Call service method
        menuCategoryService.updateCategory("Category1", updatedCategoryDTO);

        // Verify that category is updated with new name
        assertEquals("Category2", existingCategory.getName());

        logger.info("updateCategory test passed.");
    }

    @Test
    void deleteCategory() throws Exception {
        // Mock data
        String categoryName = "Category1";
        when(menuCategoryRepository.existsById(categoryName)).thenReturn(true);

        // Call service method
        menuCategoryService.deleteCategory(categoryName);

        // Verify that deleteById method is called once
        verify(menuCategoryRepository, times(1)).deleteById(categoryName);

        logger.info("deleteCategory test passed.");
    }
}
