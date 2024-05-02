package app.restman.api.services;

import app.restman.api.DTOs.MenuCategoryDTO;
import app.restman.api.entities.MenuCategory;
import app.restman.api.repositories.MenuCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;

    private static final Logger logger = Logger.getLogger(MenuCategoryService.class.getName());

    @Autowired
    public MenuCategoryService(MenuCategoryRepository menuCategoryRepository) {
        this.menuCategoryRepository = menuCategoryRepository;
    }

    public List<MenuCategory> getAllCategories() {
        return menuCategoryRepository.findAll();
    }

    public MenuCategory getCategoryByName(String name) throws NoSuchElementException {
        return menuCategoryRepository.findById(name).orElse(null);
    }

    public MenuCategory createCategory(MenuCategoryDTO categoryDTO) throws Exception {
        if (categoryDTO.getName().isBlank()) {
            logger.log(Level.SEVERE, "Name cannot be blank!");
            throw new Exception("Name cannot be blank!");
        }

        MenuCategory category = new MenuCategory();
        BeanUtils.copyProperties(categoryDTO, category);
        return menuCategoryRepository.save(category);
    }

    public void updateCategory(String name, MenuCategoryDTO updatedCategoryDTO) throws Exception, NoSuchElementException {
        if (updatedCategoryDTO.getName().isBlank()) {
            logger.log(Level.SEVERE, "Name cannot be blank!");
            throw new Exception("Name cannot be blank!");
        }

        MenuCategory existingCategory = menuCategoryRepository.findById(name).orElse(null);
        if (existingCategory != null) {
            BeanUtils.copyProperties(updatedCategoryDTO, existingCategory);
            menuCategoryRepository.save(existingCategory);
        } else {
            logger.log(Level.SEVERE, "Menu category does not exist!");
            throw new NoSuchElementException("Menu category does not exist!");
        }
    }

    public void deleteCategory(String name) throws NoSuchElementException {
        if (!menuCategoryRepository.existsById(name)) {
            logger.log(Level.SEVERE, "Category does not exist!");
            throw new NoSuchElementException("Category does not exist!");
        }

        menuCategoryRepository.deleteById(name);
    }
}
