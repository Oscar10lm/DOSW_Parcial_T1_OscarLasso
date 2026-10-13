package edu.dosw.parcial.core;

import edu.dosw.parcial.controller.dtos.request.CreateOrderRequest;
import edu.dosw.parcial.controller.dtos.request.OrderPlateRequest;
import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderStatus;
import edu.dosw.parcial.core.services.impl.OrderServiceImpl;
import edu.dosw.parcial.core.validators.OrderValidator;
import edu.dosw.parcial.core.validators.ResourceNotFoundException;
import edu.dosw.parcial.persistence.entities.OrderDocument;
import edu.dosw.parcial.persistence.mappers.OrderPersistenceMapper;
import edu.dosw.parcial.persistence.repositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderPersistenceMapper persistenceMapper;

    @Mock
    private OrderValidator orderValidator;

    @InjectMocks
    private OrderServiceImpl orderService;

    private OrderDocument sampleDoc;
    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        sampleDoc = OrderDocument.builder()
            .id("101")
            .customerName("Oscar Lasso")
            .deliveryType("Local")
            .totalPrice(8500)
            .status("PENDIENTE")
            .build();

        sampleOrder = Order.builder()
            .id("101")
            .customerName("Oscar Lasso")
            .deliveryType("Local")
            .totalPrice(8500)
            .status(OrderStatus.PENDIENTE)
            .build();
    }

    @Test
    @DisplayName("Debe crear un pedido y persistirlo en MongoDB")
    void shouldCreateOrderAndPersist() {
        CreateOrderRequest request = CreateOrderRequest.builder()
            .customerName("Oscar Lasso")
            .deliveryType("Local")
            .plates(List.of(
                OrderPlateRequest.builder().plateName("Bandeja Universitaria").build()
            ))
            .build();

        when(persistenceMapper.toDocument(any(Order.class))).thenReturn(sampleDoc);
        when(orderRepository.save(any(OrderDocument.class))).thenReturn(sampleDoc);
        when(persistenceMapper.toDomain(sampleDoc)).thenReturn(sampleOrder);

        Order result = orderService.createOrder(request);

        assertNotNull(result);
        assertEquals("101", result.getId());
        verify(orderValidator, times(1)).validateOrder(any(Order.class));
        verify(orderRepository, times(1)).save(sampleDoc);
    }

    @Test
    @DisplayName("Debe buscar un pedido existente por ID")
    void shouldFindOrderById() {
        when(orderRepository.findById("101")).thenReturn(Optional.of(sampleDoc));
        when(persistenceMapper.toDomain(sampleDoc)).thenReturn(sampleOrder);

        Order result = orderService.getOrderById("101");

        assertNotNull(result);
        assertEquals("101", result.getId());
    }

    @Test
    @DisplayName("Debe lanzar ResourceNotFoundException si el pedido no existe")
    void shouldThrowWhenOrderNotFound() {
        when(orderRepository.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById("999"));
    }

    @Test
    @DisplayName("Debe listar todos los pedidos")
    void shouldListAllOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(sampleDoc));
        when(persistenceMapper.toDomain(sampleDoc)).thenReturn(sampleOrder);

        List<Order> result = orderService.getAllOrders();

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Debe actualizar el estado de un pedido")
    void shouldUpdateOrderStatus() {
        when(orderRepository.findById("101")).thenReturn(Optional.of(sampleDoc));
        when(persistenceMapper.toDomain(sampleDoc)).thenReturn(sampleOrder);
        when(orderRepository.save(any(OrderDocument.class))).thenReturn(sampleDoc);

        Order updated = orderService.updateOrderStatus("101", OrderStatus.EN_PREPARACION);

        assertNotNull(updated);
        verify(orderValidator).validateStatusTransition(OrderStatus.PENDIENTE, OrderStatus.EN_PREPARACION);
        verify(orderRepository).save(sampleDoc);
    }

    @Test
    @DisplayName("Debe eliminar un pedido por ID si existe")
    void shouldDeleteOrder() {
        when(orderRepository.existsById("101")).thenReturn(true);
        doNothing().when(orderRepository).deleteById("101");

        assertDoesNotThrow(() -> orderService.deleteOrder("101"));
        verify(orderRepository).deleteById("101");
    }
}
