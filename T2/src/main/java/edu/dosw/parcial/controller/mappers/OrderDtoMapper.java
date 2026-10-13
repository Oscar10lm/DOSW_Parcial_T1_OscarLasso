package edu.dosw.parcial.controller.mappers;

import edu.dosw.parcial.controller.dtos.response.OrderPlateResponse;
import edu.dosw.parcial.controller.dtos.response.OrderResponse;
import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderItem;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderDtoMapper {

    public OrderResponse toResponse(Order domain) {
        if (domain == null) {
            return null;
        }

        List<OrderPlateResponse> plateResponses = domain.getItems() != null
            ? domain.getItems().stream().map(this::toPlateResponse).collect(Collectors.toList())
            : Collections.emptyList();

        return OrderResponse.builder()
            .id(domain.getId())
            .customerName(domain.getCustomerName())
            .deliveryType(domain.getDeliveryType())
            .plates(plateResponses)
            .totalPrice(domain.getTotalPrice())
            .status(domain.getStatus() != null ? domain.getStatus().name() : "PENDIENTE")
            .createdAt(domain.getCreatedAt())
            .build();
    }

    public OrderPlateResponse toPlateResponse(OrderItem item) {
        if (item == null) {
            return null;
        }
        return OrderPlateResponse.builder()
            .plateName(item.getPlateName())
            .basePrice(item.getBasePrice())
            .extras(item.getExtras() != null ? item.getExtras() : Collections.emptyList())
            .totalPrice(item.getTotalPrice())
            .build();
    }
}
