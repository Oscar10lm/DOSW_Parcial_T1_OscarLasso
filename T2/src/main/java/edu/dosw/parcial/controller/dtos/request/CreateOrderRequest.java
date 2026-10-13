package edu.dosw.parcial.controller.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Petición para crear un nuevo pedido en la cafetería")
public class CreateOrderRequest {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Schema(description = "Nombre del estudiante o cliente", example = "Oscar Lasso")
    private String customerName;

    @NotBlank(message = "El método de entrega es obligatorio")
    @Schema(description = "Método de entrega (Para llevar, Local, Salón)", example = "Local")
    private String deliveryType;

    @NotEmpty(message = "Debe incluir al menos un plato en el pedido")
    @Size(max = 5, message = "No se permiten más de 5 platos por pedido")
    @Valid
    @Builder.Default
    @Schema(description = "Lista de platos que componen el pedido (máximo 5)")
    private List<OrderPlateRequest> plates = new ArrayList<>();
}
