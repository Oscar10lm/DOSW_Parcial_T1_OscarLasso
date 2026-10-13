package edu.dosw.parcial.core.services;

import edu.dosw.parcial.controller.dtos.request.CreateOrderRequest;
import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(CreateOrderRequest request);
    Order getOrderById(String id);
    List<Order> getAllOrders();
    List<Order> getOrdersByStatus(OrderStatus status);
    Order updateOrderStatus(String id, OrderStatus newStatus);
    void deleteOrder(String id);
}
