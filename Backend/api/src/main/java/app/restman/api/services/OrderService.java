package app.restman.api.services;

import app.restman.api.DTOs.OrderDTO;
import app.restman.api.entities.Order;
import app.restman.api.entities.Product;
import app.restman.api.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final TableService tableService;

    @Autowired
    public OrderService(OrderRepository orderRepository, TableService tableService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.tableService = tableService;
        this.productService = productService;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(OrderDTO orderDTO) throws Exception {
        List<Product> products = productService.getProductsByNames(orderDTO.getProductNames());

        if (tableService.getTableById(orderDTO.getTableId()) == null)
            throw new Exception("Table does not exist!");

        if (products.size() != orderDTO.getQuantities().size()) {
            throw new Exception("Number of products must match number of quantities");
        }

        Order order = new Order(products, orderDTO.getQuantities(), orderDTO.getTableId());
        order.setOrderId(UUID.randomUUID().toString());

        return orderRepository.save(order);
    }

    public Order getOrderById(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public void updateOrder(String orderId, OrderDTO updatedOrderDTO) throws Exception {
        Order order = orderRepository.findById(orderId).orElse(null);
        if (order != null) {
            if (tableService.getTableById(updatedOrderDTO.getTableId()) == null)
                throw new Exception("Table does not exist!");

            order.setTableId(updatedOrderDTO.getTableId());

            List<Product> products = productService.getProductsByNames(updatedOrderDTO.getProductNames());
            order.setProductNamesToQuantities(generateProductNamesToQuantities(products, updatedOrderDTO.getQuantities()));
            order.calculatePrice(); // Recalculate price after updating product quantities
            orderRepository.save(order);
        }
    }

    public void deleteOrder(String orderId) {
        if (!orderRepository.existsById(orderId))
            throw new NoSuchElementException("Order does not exist!");

        orderRepository.deleteById(orderId);
    }

    private HashMap<String, Integer> generateProductNamesToQuantities(List<Product> products, List<Integer> quantities) {
        HashMap<String, Integer> productNamesToQuantities = new HashMap<>();
        for (int index = 0; index < products.size(); index++) {
            Product product = products.get(index);
            Integer quantity = quantities.get(index);
            productNamesToQuantities.put(product.getName(), quantity);
        }
        return productNamesToQuantities;
    }
}
