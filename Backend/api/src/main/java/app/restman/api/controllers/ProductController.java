package app.restman.api.controllers;

import app.restman.api.DTOs.ProductDTO;
import app.restman.api.entities.Product;
import app.restman.api.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("products")
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::new) // Use the constructor with Product argument
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/getAllByCategory/{categoryName}")
    public ResponseEntity<List<ProductDTO>> getAllProductsByCategory(@PathVariable String categoryName) {
        List<Product> products = productService.getAllProductsByCategory(categoryName);
        List<ProductDTO> productDTOs = products.stream()
                .map(ProductDTO::new) // Use the constructor with Product argument
                .collect(Collectors.toList());
        return ResponseEntity.ok(productDTOs);
    }

    @GetMapping("/{productName}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String productName) {
        Product product = productService.getProductByName(productName);
        if (product != null) {
            return ResponseEntity.ok(new ProductDTO(product)); // Use the constructor with Product argument
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO) {
        try {
            Product product = productService.createProduct(productDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Product created successfully with name: " + product.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{productName}")
    public ResponseEntity<String> updateProduct(@PathVariable String productName, @RequestBody ProductDTO updatedProductDTO) {
        try {
            productService.updateProduct(productName, updatedProductDTO);
            return ResponseEntity.ok("Product updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{productName}")
    public ResponseEntity<String> deleteProduct(@PathVariable String productName) {
        try {
            productService.deleteProduct(productName);
            return ResponseEntity.ok("Product deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}