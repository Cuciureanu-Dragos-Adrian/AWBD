package app.restman.api.controllers;

import app.restman.api.DTOs.OrderDTO;
import app.restman.api.DTOs.OrderReturnDTO;
import app.restman.api.entities.Order;
import app.restman.api.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<OrderReturnDTO>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderReturnDTO> orderDTOs = orders.stream()
                .map(OrderReturnDTO::new) // Use your OrderDTO constructor
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDTOs);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderReturnDTO> getOrderById(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return ResponseEntity.ok(new OrderReturnDTO(order)); // Use your OrderDTO constructor
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/byTableId/{tableId}")
    public ResponseEntity<OrderReturnDTO> getOrderByTableId(@PathVariable String tableId) {
        Order order = orderService.getOrderByTableId(tableId);
        if (order != null) {
            return ResponseEntity.ok(new OrderReturnDTO(order)); // Use your OrderDTO constructor
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDTO orderDTO) {
        try {
            Order order = orderService.createOrder(orderDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully with ID: " + order.getOrderId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable String orderId, @RequestBody OrderDTO updatedOrderDTO) {
        try {
            orderService.updateOrder(orderId, updatedOrderDTO);
            return ResponseEntity.ok("Order updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) {
        try {
            orderService.deleteOrder(orderId);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/byTableId/{tableId}")
    public ResponseEntity<String> deleteOrdersByTableId(@PathVariable String tableId) {
        try {
            orderService.deleteOrderByTableId(tableId);
            return ResponseEntity.ok("Order deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
