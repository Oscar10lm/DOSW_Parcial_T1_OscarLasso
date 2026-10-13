package edu.dosw.parcial.controller.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Detalle del plato dentro del pedido")
public class OrderPlateResponse {

    @Schema(description = "Nombre del plato base", example = "Sandwich")
    private String plateName;

    @Schema(description = "Precio base del plato", example = "5000")
    private int basePrice;

    @Builder.Default
    @Schema(description = "Extras agregados al plato", example = "[\"Porción extra de proteína\"]")
    private List<String> extras = new ArrayList<>();

    @Schema(description = "Precio total del plato incluyendo extras calculados con Decorator", example = "7500")
    private int totalPrice;
}
