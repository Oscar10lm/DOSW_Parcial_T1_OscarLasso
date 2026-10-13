package edu.dosw.parcial.controller.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
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
@Schema(description = "Especificación de un plato y sus adiciones extras")
public class OrderPlateRequest {

    @NotBlank(message = "El nombre del plato es obligatorio")
    @Schema(description = "Nombre del plato base", example = "Sandwich")
    private String plateName;

    @Builder.Default
    @Schema(description = "Lista de extras o ingredientes adicionales", example = "[\"Porción extra de proteína\", \"Adición de queso\"]")
    private List<String> extras = new ArrayList<>();
}
