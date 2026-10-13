package edu.dosw.parcial.core.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String id;
    private String customerName;
    private String deliveryType;
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
    private int totalPrice;
    @Builder.Default
    private OrderStatus status = OrderStatus.PENDIENTE;
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public int calculateTotal() {
        if (items == null || items.isEmpty()) {
            return 0;
        }
        return items.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }
}
