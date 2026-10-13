package edu.dosw.parcial.controller.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Respuesta detallada de un pedido confirmado")
public class OrderResponse {

    @Schema(description = "Identificador único del pedido en MongoDB", example = "65287f3b89a4cd1234567890")
    private String id;

    @Schema(description = "Nombre del cliente", example = "Oscar Lasso")
    private String customerName;

    @Schema(description = "Método de entrega seleccionado", example = "Local")
    private String deliveryType;

    @Builder.Default
    @Schema(description = "Lista de platos que componen el pedido")
    private List<OrderPlateResponse> plates = new ArrayList<>();

    @Schema(description = "Monto total a pagar por el pedido", example = "10000")
    private int totalPrice;

    @Schema(description = "Estado actual del pedido", example = "PENDIENTE")
    private String status;

    @Schema(description = "Fecha y hora de registro del pedido")
    private LocalDateTime createdAt;
}
