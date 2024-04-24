package app.restman.api.services;

import app.restman.api.DTOs.MenuCategoryDTO;
import app.restman.api.entities.MenuCategory;
import app.restman.api.repositories.MenuCategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;

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
        if (categoryDTO.getName().isBlank())
            throw new Exception("Name cannot be blank!");

        MenuCategory category = new MenuCategory();
        BeanUtils.copyProperties(categoryDTO, category);
        return menuCategoryRepository.save(category);
    }

    public void updateCategory(String name, MenuCategoryDTO updatedCategoryDTO) throws Exception, NoSuchElementException {
        if (updatedCategoryDTO.getName().isBlank())
            throw new Exception("Name cannot be blank!");

        MenuCategory existingCategory = menuCategoryRepository.findById(name).orElse(null);
        if (existingCategory != null) {
            BeanUtils.copyProperties(updatedCategoryDTO, existingCategory);
            menuCategoryRepository.save(existingCategory);
        }
        else
            throw new NoSuchElementException("Menu category does not exist!");
    }

    public void deleteCategory(String name) throws NoSuchElementException{
        if (!menuCategoryRepository.existsById(name))
            throw new NoSuchElementException("Category does not exist!");

        menuCategoryRepository.deleteById(name);
    }
}
