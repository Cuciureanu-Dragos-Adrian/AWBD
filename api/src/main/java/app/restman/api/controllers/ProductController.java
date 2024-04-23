package app.restman.api.controllers;

import app.restman.api.entities.ProductEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private static List<ProductEntity> products = new ArrayList<>();

    @PostMapping("/add")
    public void createProduct(@RequestBody ProductEntity product) {
        //TODO - DB
        products.add(product);
    }

    @GetMapping("/getAll")
    public List<ProductEntity> getProducts() {
        //TODO - DB
        return products;
    }

    @GetMapping("/{name}")
    public ProductEntity getProductByName(@PathVariable String name) {
        //TODO - DB
        for (ProductEntity product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null; // Or throw an exception if product not found
    }

    @PutMapping("/{name}")
    public void updateProduct(@PathVariable String name, @RequestBody ProductEntity updatedProduct) {
        //TODO - DB
        for (int i = 0; i < products.size(); i++) {
            ProductEntity product = products.get(i);
            if (product.getName().equals(name)) {
                products.set(i, updatedProduct);
                return;
            }
        }
        // If product with the given name is not found, you can throw an exception or handle it as needed
    }

    @DeleteMapping("/{name}")
    public void deleteProduct(@PathVariable String name) {
        //TODO - DB
        products.removeIf(product -> product.getName().equals(name));
    }
}