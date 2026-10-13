package edu.dosw.parcial.controller.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Estructura estándar para respuestas de error")
public class ErrorResponse {

    @Schema(description = "Estampa de tiempo del error")
    private LocalDateTime timestamp;

    @Schema(description = "Código de estado HTTP", example = "400")
    private int status;

    @Schema(description = "Nombre del error HTTP", example = "Bad Request")
    private String error;

    @Schema(description = "Mensaje detallado del error", example = "El pedido no alcanza el monto mínimo requerido ($3500)")
    private String message;

    @Schema(description = "Ruta del recurso invocado", example = "/api/v1/orders")
    private String path;
}
