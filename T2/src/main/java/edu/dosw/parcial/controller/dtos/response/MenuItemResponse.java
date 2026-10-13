package edu.dosw.parcial.controller.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Elemento del catálogo del menú")
public class MenuItemResponse {

    @Schema(description = "Nombre del producto o ingrediente", example = "Bandeja Universitaria")
    private String name;

    @Schema(description = "Precio en COP", example = "8500")
    private int price;

    @Schema(description = "Categoría o tipo (PLATO o EXTRA)", example = "PLATO")
    private String type;
}
