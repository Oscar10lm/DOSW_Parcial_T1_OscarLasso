package edu.dosw.parcial.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDocument {
    private String plateName;
    private int basePrice;
    @Builder.Default
    private List<String> extras = new ArrayList<>();
    private int totalPrice;
}
