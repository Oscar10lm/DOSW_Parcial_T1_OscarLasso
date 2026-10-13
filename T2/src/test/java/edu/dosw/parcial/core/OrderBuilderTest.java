package edu.dosw.parcial.core;

import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderBuilder;
import edu.dosw.parcial.core.models.OrderStatus;
import edu.dosw.parcial.core.validators.BusinessRuleException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderBuilderTest {

    @Test
    @DisplayName("Debe construir un pedido válido cumpliendo todas las reglas")
    void shouldBuildValidOrder() {
        Order order = new OrderBuilder()
            .customerName("Oscar Lasso")
            .deliveryType("Local")
            .addPlate("Sandwich", List.of("Porción extra de proteína", "Adición de queso"))
            .build();

        assertNotNull(order);
        assertEquals("Oscar Lasso", order.getCustomerName());
        assertEquals("Local", order.getDeliveryType());
        assertEquals(1, order.getItems().size());
        assertEquals(9500, order.getTotalPrice()); // 5000 + 2500 + 2000
        assertEquals(OrderStatus.PENDIENTE, order.getStatus());
    }

    @Test
    @DisplayName("Debe lanzar error si no se agrega ningún plato")
    void shouldThrowErrorWhenNoPlates() {
        OrderBuilder builder = new OrderBuilder()
            .customerName("Oscar")
            .deliveryType("Local");

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, builder::build);
        assertTrue(ex.getMessage().contains("al menos un plato"));
    }

    @Test
    @DisplayName("Debe lanzar error si se agregan más de 5 platos")
    void shouldThrowErrorWhenExceedingMaxPlates() {
        OrderBuilder builder = new OrderBuilder()
            .customerName("Oscar")
            .deliveryType("Local");

        for (int i = 0; i < 5; i++) {
            builder.addPlate("Sandwich", null);
        }

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, () ->
            builder.addPlate("Sandwich", null)
        );
        assertTrue(ex.getMessage().contains("más de 5 platos"));
    }

    @Test
    @DisplayName("Debe lanzar error si el método de entrega es nulo o vacío")
    void shouldThrowErrorWhenDeliveryTypeIsMissing() {
        OrderBuilder builder = new OrderBuilder()
            .customerName("Oscar")
            .addPlate("Bandeja Universitaria", null);

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, builder::build);
        assertTrue(ex.getMessage().contains("método de entrega es obligatorio"));
    }

    @Test
    @DisplayName("Debe lanzar error si el método de entrega es desconocido")
    void shouldThrowErrorWhenDeliveryTypeIsInvalid() {
        OrderBuilder builder = new OrderBuilder()
            .customerName("Oscar")
            .deliveryType("Dron")
            .addPlate("Bandeja Universitaria", null);

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, builder::build);
        assertTrue(ex.getMessage().contains("no es válido"));
    }

    @Test
    @DisplayName("Debe lanzar error si el total del pedido es menor a $3500")
    void shouldThrowErrorWhenTotalBelowMinimum() {
        OrderBuilder builder = new OrderBuilder()
            .customerName("Oscar")
            .deliveryType("Local")
            .addPlate("Jugo natural", null); // Jugo cuesta 2500 < 3500

        BusinessRuleException ex = assertThrows(BusinessRuleException.class, builder::build);
        assertTrue(ex.getMessage().contains("no alcanza el monto mínimo"));
    }
}
