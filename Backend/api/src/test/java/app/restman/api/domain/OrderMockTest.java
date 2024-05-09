package app.restman.api.domain;


import app.restman.api.DTOs.OrderDTO;
import app.restman.api.entities.Order;
import app.restman.api.entities.Table;
import app.restman.api.repositories.OrderRepository;
import app.restman.api.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class OrderMockTest {

    private static final Logger logger = Logger.getLogger(OrderMockTest.class.getName());

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createOrder() throws Exception {
        // Mock data
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1");

        Order mockOrder = new Order();
        mockOrder.setOrderId("1");

        when(orderRepository.existsById("1")).thenReturn(false);
        when(orderRepository.save(any(Order.class))).thenReturn(mockOrder);

        assertThrows(Exception.class, () -> orderService.createOrder(orderDTO));

        logger.info("createOrder test passed.");
    }

    @Test
    void getAllOrders() {
        // Mock data
        List<Order> mockOrders = new ArrayList<>();
        Order order1 = new Order();
        order1.setOrderId("1");
        Order order2 = new Order();
        order2.setOrderId("2");
        mockOrders.add(order1);
        mockOrders.add(order2);

        when(orderRepository.findAll()).thenReturn(mockOrders);

        // Call service method
        List<Order> orders = orderService.getAllOrders();

        // Verify that all orders are returned
        assertEquals(2, orders.size());

        logger.info("getAllOrders test passed.");
    }

    @Test
    void updateOrderError() throws Exception {
        // Mock data
        String id = "123";
        OrderDTO updatedOrderDTO = new OrderDTO();
        updatedOrderDTO.setOrderId("123");
        updatedOrderDTO.setTableId("A1");

        // Initialize existingOrder with relevant properties
        Order existingOrder = new Order();
        existingOrder.setOrderId("123");
        existingOrder.setTableId("A1");

        // Mock behavior of orderRepository
        when(orderRepository.existsById(id)).thenReturn(true);
        when(orderRepository.findById(id)).thenReturn(Optional.of(existingOrder));

        assertThrows(Exception.class, () -> orderService.updateOrder(id, updatedOrderDTO));

        logger.info("updateOrder test passed.");
    }


    @Test
    void deleteOrder() {
        // Mock data
        String orderId = "123";
        when(orderRepository.existsById(orderId)).thenReturn(true);

        // Call service method
        orderService.deleteOrder(orderId);

        // Verify that deleteById method is called once
        verify(orderRepository, times(1)).deleteById(orderId);

        logger.info("deleteOrder test passed.");
    }
}

