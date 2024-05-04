package app.restman.api.services;

import app.restman.api.DTOs.MenuCategoryDTO;
import app.restman.api.entities.MenuCategory;
import app.restman.api.repositories.MenuCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;
    private final ProductService productService;

    private static final Logger logger = Logger.getLogger(MenuCategoryService.class.getName());

    @Autowired
    public MenuCategoryService(MenuCategoryRepository menuCategoryRepository, ProductService productService) {
        this.menuCategoryRepository = menuCategoryRepository;
        this.productService = productService;
    }

    public List<MenuCategory> getAllCategories() {
        var categories = menuCategoryRepository.findAll();
        categories.sort(Comparator.comparing(MenuCategory::getName));
        return categories;
    }

    public MenuCategory getCategoryByName(String name) throws NoSuchElementException {
        return menuCategoryRepository.findById(name).orElse(null);
    }

    public MenuCategory createCategory(MenuCategoryDTO categoryDTO) throws Exception {
        if (categoryDTO.getName().isBlank()) {
            logger.log(Level.WARNING, "Name cannot be blank!");
            throw new Exception("Name cannot be blank!");
        }
        else if (categoryDTO.getName().length() < 3) {
            logger.log(Level.WARNING, "Category name needs at least 3 characters!");
            throw new Exception("Category name needs at least 3 characters!");
        }
        else if (menuCategoryRepository.existsById(categoryDTO.getName())) {
            logger.log(Level.WARNING, "Category with given name already exists!");
            throw new Exception("Category with given name already exists!");
        }

        MenuCategory category = new MenuCategory();
        BeanUtils.copyProperties(categoryDTO, category);
        return menuCategoryRepository.save(category);
    }

    public void updateCategory(String name, MenuCategoryDTO updatedCategoryDTO) throws Exception, NoSuchElementException {
        if (updatedCategoryDTO.getName().isBlank()) {
            logger.log(Level.WARNING, "Name cannot be blank!");
            throw new Exception("Name cannot be blank!");
        }

        MenuCategory existingCategory = menuCategoryRepository.findById(name).orElse(null);
        if (existingCategory != null) {
            BeanUtils.copyProperties(updatedCategoryDTO, existingCategory);
            menuCategoryRepository.save(existingCategory);
        } else {
            logger.log(Level.WARNING, "Menu category does not exist!");
            throw new NoSuchElementException("Menu category does not exist!");
        }
    }

    public void deleteCategory(String name) throws NoSuchElementException {
        if (!menuCategoryRepository.existsById(name)) {
            logger.log(Level.WARNING, "Category does not exist!");
            throw new NoSuchElementException("Category does not exist!");
        }

        //delete all products in the given category
        var productsInCategory = productService.getAllProductsByCategory(name);
        for (var product : productsInCategory)
            productService.deleteProduct(product.getName());

        menuCategoryRepository.deleteById(name);
    }
}
