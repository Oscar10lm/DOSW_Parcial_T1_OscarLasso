package edu.dosw.parcial.controller.dtos.request;

import edu.dosw.parcial.core.models.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Petición para actualizar el estado de un pedido")
public class UpdateStatusRequest {

    @NotNull(message = "El nuevo estado es obligatorio")
    @Schema(description = "Nuevo estado del pedido", example = "EN_PREPARACION")
    private OrderStatus status;
}
