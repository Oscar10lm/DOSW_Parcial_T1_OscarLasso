package edu.dosw.parcial.core.services.impl;

import edu.dosw.parcial.controller.dtos.request.CreateOrderRequest;
import edu.dosw.parcial.controller.dtos.request.OrderPlateRequest;
import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderBuilder;
import edu.dosw.parcial.core.models.OrderStatus;
import edu.dosw.parcial.core.services.OrderService;
import edu.dosw.parcial.core.validators.OrderValidator;
import edu.dosw.parcial.core.validators.ResourceNotFoundException;
import edu.dosw.parcial.persistence.entities.OrderDocument;
import edu.dosw.parcial.persistence.mappers.OrderPersistenceMapper;
import edu.dosw.parcial.persistence.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderPersistenceMapper persistenceMapper;
    private final OrderValidator orderValidator;

    @Override
    public Order createOrder(CreateOrderRequest request) {
        // Uso del patrón Builder para construir el pedido y Decorator para cada plato
        OrderBuilder builder = new OrderBuilder()
            .customerName(request.getCustomerName())
            .deliveryType(request.getDeliveryType())
            .status(OrderStatus.PENDIENTE)
            .createdAt(LocalDateTime.now());

        if (request.getPlates() != null) {
            for (OrderPlateRequest plateReq : request.getPlates()) {
                builder.addPlate(plateReq.getPlateName(), plateReq.getExtras());
            }
        }

        Order order = builder.build();

        // Validaciones complejas adicionales de negocio
        orderValidator.validateOrder(order);

        // Persistencia en MongoDB
        OrderDocument docToSave = persistenceMapper.toDocument(order);
        OrderDocument savedDoc = orderRepository.save(docToSave);

        return persistenceMapper.toDomain(savedDoc);
    }

    @Override
    public Order getOrderById(String id) {
        OrderDocument doc = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));
        return persistenceMapper.toDomain(doc);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll().stream()
            .map(persistenceMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status.name()).stream()
            .map(persistenceMapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Order updateOrderStatus(String id, OrderStatus newStatus) {
        OrderDocument doc = orderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + id));

        Order currentOrder = persistenceMapper.toDomain(doc);
        orderValidator.validateStatusTransition(currentOrder.getStatus(), newStatus);

        doc.setStatus(newStatus.name());
        OrderDocument updatedDoc = orderRepository.save(doc);

        return persistenceMapper.toDomain(updatedDoc);
    }

    @Override
    public void deleteOrder(String id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pedido no encontrado con ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}
