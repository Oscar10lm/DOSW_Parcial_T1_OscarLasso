package edu.dosw.parcial.core.models;

import edu.dosw.parcial.core.utils.MenuCatalog;
import edu.dosw.parcial.core.validators.BusinessRuleException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Patrón Builder para la construcción controlada de pedidos (Order)
 * Integrado con el patrón Decorator para calcular los precios de cada plato.
 */
public class OrderBuilder {

    private String id;
    private String customerName;
    private String deliveryType;
    private OrderStatus status = OrderStatus.PENDIENTE;
    private LocalDateTime createdAt;
    private final List<OrderItem> items = new ArrayList<>();

    public OrderBuilder id(String id) {
        this.id = id;
        return this;
    }

    public OrderBuilder customerName(String customerName) {
        this.customerName = customerName;
        return this;
    }

    public OrderBuilder deliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public OrderBuilder status(OrderStatus status) {
        if (status != null) {
            this.status = status;
        }
        return this;
    }

    public OrderBuilder createdAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    /**
     * Agrega un plato al pedido aplicando decoradores dinámicos para los extras.
     */
    public OrderBuilder addPlate(String plateName, List<String> extras) {
        if (items.size() >= MenuCatalog.MAX_PLATES_PER_ORDER) {
            throw new BusinessRuleException("No se pueden agregar más de " 
                + MenuCatalog.MAX_PLATES_PER_ORDER + " platos por pedido.");
        }

        if (!MenuCatalog.isValidPlate(plateName)) {
            throw new BusinessRuleException("El plato '" + plateName + "' no existe en el menú.");
        }

        int basePrice = MenuCatalog.getPlatePrice(plateName);
        Consumible consumible = new Plate(plateName, basePrice);

        List<String> registeredExtras = new ArrayList<>();
        if (extras != null) {
            for (String extra : extras) {
                if (extra == null || extra.trim().isEmpty()) {
                    continue;
                }
                if (!MenuCatalog.isValidExtra(extra)) {
                    throw new BusinessRuleException("El ingrediente extra '" + extra + "' no existe en el menú.");
                }
                int extraPrice = MenuCatalog.getExtraPrice(extra);
                // Patrón Decorator aplicado dinámicamente
                consumible = new GenericExtraDecorator(consumible, extra, extraPrice);
                registeredExtras.add(extra);
            }
        }

        OrderItem item = OrderItem.builder()
            .plateName(plateName)
            .basePrice(basePrice)
            .extras(registeredExtras)
            .totalPrice(consumible.getPrice())
            .build();

        items.add(item);
        return this;
    }

    public OrderBuilder addItem(OrderItem item) {
        if (items.size() >= MenuCatalog.MAX_PLATES_PER_ORDER) {
            throw new BusinessRuleException("No se pueden agregar más de " 
                + MenuCatalog.MAX_PLATES_PER_ORDER + " platos por pedido.");
        }
        this.items.add(item);
        return this;
    }

    public Order build() {
        if (items.isEmpty()) {
            throw new BusinessRuleException("El pedido debe contener al menos un plato.");
        }

        if (items.size() > MenuCatalog.MAX_PLATES_PER_ORDER) {
            throw new BusinessRuleException("El pedido no puede exceder " 
                + MenuCatalog.MAX_PLATES_PER_ORDER + " platos.");
        }

        if (deliveryType == null || deliveryType.trim().isEmpty()) {
            throw new BusinessRuleException("El método de entrega es obligatorio.");
        }

        if (!MenuCatalog.isValidDeliveryType(deliveryType)) {
            throw new BusinessRuleException("El método de entrega '" + deliveryType 
                + "' no es válido. Opciones permitidas: " + MenuCatalog.getDeliveryTypes());
        }

        if (customerName == null || customerName.trim().isEmpty()) {
            customerName = "Cliente General";
        }

        int total = items.stream().mapToInt(OrderItem::getTotalPrice).sum();
        if (total < MenuCatalog.MIN_ORDER_PRICE) {
            throw new BusinessRuleException("El total del pedido ($" + total 
                + ") no alcanza el monto mínimo requerido ($" + MenuCatalog.MIN_ORDER_PRICE + ").");
        }

        return Order.builder()
            .id(id)
            .customerName(customerName.trim())
            .deliveryType(deliveryType.trim())
            .items(new ArrayList<>(items))
            .totalPrice(total)
            .status(status != null ? status : OrderStatus.PENDIENTE)
            .createdAt(createdAt != null ? createdAt : LocalDateTime.now())
            .build();
    }
}
