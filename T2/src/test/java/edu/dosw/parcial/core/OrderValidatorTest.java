package edu.dosw.parcial.core;

import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderItem;
import edu.dosw.parcial.core.models.OrderStatus;
import edu.dosw.parcial.core.validators.BusinessRuleException;
import edu.dosw.parcial.core.validators.OrderValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {

    private OrderValidator validator;

    @BeforeEach
    void setUp() {
        validator = new OrderValidator();
    }

    @Test
    @DisplayName("Debe validar exitosamente un pedido correcto")
    void shouldValidateCorrectOrder() {
        Order order = Order.builder()
            .deliveryType("Local")
            .totalPrice(5000)
            .items(List.of(OrderItem.builder().plateName("Sandwich").totalPrice(5000).build()))
            .build();

        assertDoesNotThrow(() -> validator.validateOrder(order));
    }

    @Test
    @DisplayName("Debe lanzar excepción si el pedido es nulo")
    void shouldThrowIfOrderNull() {
        assertThrows(BusinessRuleException.class, () -> validator.validateOrder(null));
    }

    @Test
    @DisplayName("Debe lanzar excepción si no se permite transición desde CANCELADO")
    void shouldThrowIfTransitionFromCancelled() {
        assertThrows(BusinessRuleException.class, () ->
            validator.validateStatusTransition(OrderStatus.CANCELADO, OrderStatus.EN_PREPARACION)
        );
    }

    @Test
    @DisplayName("Debe lanzar excepción si no se permite transición desde ENTREGADO")
    void shouldThrowIfTransitionFromDelivered() {
        assertThrows(BusinessRuleException.class, () ->
            validator.validateStatusTransition(OrderStatus.ENTREGADO, OrderStatus.CANCELADO)
        );
    }
}
