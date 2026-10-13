package edu.dosw.parcial.persistence.mappers;

import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderItem;
import edu.dosw.parcial.core.models.OrderStatus;
import edu.dosw.parcial.persistence.entities.OrderDocument;
import edu.dosw.parcial.persistence.entities.OrderItemDocument;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderPersistenceMapper {

    public OrderDocument toDocument(Order domain) {
        if (domain == null) {
            return null;
        }

        List<OrderItemDocument> itemDocs = domain.getItems() != null
            ? domain.getItems().stream().map(this::toItemDocument).collect(Collectors.toList())
            : Collections.emptyList();

        return OrderDocument.builder()
            .id(domain.getId())
            .customerName(domain.getCustomerName())
            .deliveryType(domain.getDeliveryType())
            .items(itemDocs)
            .totalPrice(domain.getTotalPrice())
            .status(domain.getStatus() != null ? domain.getStatus().name() : OrderStatus.PENDIENTE.name())
            .createdAt(domain.getCreatedAt())
            .build();
    }

    public Order toDomain(OrderDocument doc) {
        if (doc == null) {
            return null;
        }

        List<OrderItem> items = doc.getItems() != null
            ? doc.getItems().stream().map(this::toItemDomain).collect(Collectors.toList())
            : Collections.emptyList();

        OrderStatus status = OrderStatus.PENDIENTE;
        if (doc.getStatus() != null) {
            try {
                status = OrderStatus.valueOf(doc.getStatus());
            } catch (IllegalArgumentException ignored) {
                status = OrderStatus.PENDIENTE;
            }
        }

        return Order.builder()
            .id(doc.getId())
            .customerName(doc.getCustomerName())
            .deliveryType(doc.getDeliveryType())
            .items(items)
            .totalPrice(doc.getTotalPrice())
            .status(status)
            .createdAt(doc.getCreatedAt())
            .build();
    }

    public OrderItemDocument toItemDocument(OrderItem item) {
        if (item == null) {
            return null;
        }
        return OrderItemDocument.builder()
            .plateName(item.getPlateName())
            .basePrice(item.getBasePrice())
            .extras(item.getExtras() != null ? item.getExtras() : Collections.emptyList())
            .totalPrice(item.getTotalPrice())
            .build();
    }

    public OrderItem toItemDomain(OrderItemDocument doc) {
        if (doc == null) {
            return null;
        }
        return OrderItem.builder()
            .plateName(doc.getPlateName())
            .basePrice(doc.getBasePrice())
            .extras(doc.getExtras() != null ? doc.getExtras() : Collections.emptyList())
            .totalPrice(doc.getTotalPrice())
            .build();
    }
}
