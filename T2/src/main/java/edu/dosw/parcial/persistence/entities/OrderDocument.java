package edu.dosw.parcial.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
public class OrderDocument {

    @Id
    private String id;
    private String customerName;
    private String deliveryType;
    @Builder.Default
    private List<OrderItemDocument> items = new ArrayList<>();
    private int totalPrice;
    private String status;
    private LocalDateTime createdAt;
}
