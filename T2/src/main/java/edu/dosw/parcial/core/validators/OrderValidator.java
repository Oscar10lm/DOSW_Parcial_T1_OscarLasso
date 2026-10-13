package edu.dosw.parcial.core.validators;

import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderStatus;
import edu.dosw.parcial.core.utils.MenuCatalog;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    public void validateOrder(Order order) {
        if (order == null) {
            throw new BusinessRuleException("El pedido no puede ser nulo.");
        }
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new BusinessRuleException("El pedido debe contener al menos un plato.");
        }
        if (order.getItems().size() > MenuCatalog.MAX_PLATES_PER_ORDER) {
            throw new BusinessRuleException("No se permiten más de " + MenuCatalog.MAX_PLATES_PER_ORDER + " platos por pedido.");
        }
        if (order.getDeliveryType() == null || !MenuCatalog.isValidDeliveryType(order.getDeliveryType())) {
            throw new BusinessRuleException("Método de entrega no válido.");
        }
        if (order.getTotalPrice() < MenuCatalog.MIN_ORDER_PRICE) {
            throw new BusinessRuleException("El pedido no cumple con el monto mínimo de $" + MenuCatalog.MIN_ORDER_PRICE);
        }
    }

    public void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) {
        if (currentStatus == null || newStatus == null) {
            throw new BusinessRuleException("El estado no puede ser nulo.");
        }
        if (currentStatus == OrderStatus.CANCELADO) {
            throw new BusinessRuleException("No se puede modificar un pedido que ya ha sido CANCELADO.");
        }
        if (currentStatus == OrderStatus.ENTREGADO) {
            throw new BusinessRuleException("No se puede modificar un pedido que ya ha sido ENTREGADO.");
        }
    }
}
