package app.restman.api.controllers;

import app.restman.api.entities.OrderEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class OrderController {

    private static List<OrderEntity> orders = new ArrayList<>();

    @PostMapping("/add")
    public void createOrder(@RequestBody OrderEntity order) {
        orders.add(order);
    }

    @GetMapping("/getAll")
    public List<OrderEntity> getAllOrders() {
        return orders;
    }

    @GetMapping("/{orderId}")
    public OrderEntity getOrderById(@PathVariable String orderId) {
        for (OrderEntity order : orders) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null; // Or throw an exception if order not found
    }

    @PutMapping("/{orderId}")
    public void updateOrder(@PathVariable String orderId, @RequestBody OrderEntity updatedOrder) {
        for (int i = 0; i < orders.size(); i++) {
            OrderEntity order = orders.get(i);
            if (order.getOrderId().equals(orderId)) {
                orders.set(i, updatedOrder);
                return;
            }
        }
        // If order with the given ID is not found, you can throw an exception or handle it as needed
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable String orderId) {
        orders.removeIf(order -> order.getOrderId().equals(orderId));
    }
}
