package app.restman.api.domain;

import app.restman.api.DTOs.ProductDTO;
import app.restman.api.entities.MenuCategory;
import app.restman.api.entities.Product;
import app.restman.api.entities.Table;
import app.restman.api.repositories.MenuCategoryRepository;
import app.restman.api.repositories.ProductRepository;
import app.restman.api.services.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ProductMockTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductMockTest.class);

    @Mock
    private ProductRepository productRepository;

    @Mock
    private MenuCategoryRepository menuCategoryRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllProducts() {
        logger.info("Testing getAllProducts...");

        List<Product> products = new ArrayList<>();

        when(productRepository.findAll()).thenReturn(products);
        List<Product> result = productService.getAllProducts();
        Assertions.assertEquals(products, result);

        logger.info("getAllProducts test passed.");
    }

    @Test
    public void getProductByName() {
        logger.info("Testing getProductByName...");

        String name = "Product1";
        Product product = new Product();

        when(productRepository.findById(name)).thenReturn(Optional.of(product));
        Product result = productService.getProductByName(name);
        Assertions.assertEquals(product, result);

        logger.info("getProductByName test passed.");
    }

    @Test
    void createProductError() throws Exception {
        // Mock data
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
        productDTO.setPrice(10.0);

        Product product = new Product();
        product.setName("Test Product");
        product.setPrice(10.0);

        // Mock behavior of productRepository
        when(productRepository.existsById("Test Product")).thenReturn(false);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        assertThrows(Exception.class, () -> productService.createProduct(productDTO));

        logger.info("createProduct test passed.");
    }

    @Test
    public void deleteProduct() {
        logger.info("Testing deleteProduct...");

        String name = "Product1";
        when(productRepository.existsById(name)).thenReturn(true);
        productService.deleteProduct(name);
        verify(productRepository, times(1)).deleteById(name);

        logger.info("deleteProduct test passed.");
    }
}
