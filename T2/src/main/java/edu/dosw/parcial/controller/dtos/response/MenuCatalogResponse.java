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
@Schema(description = "Catálogo completo de platos, extras y métodos de entrega disponibles")
public class MenuCatalogResponse {

    @Builder.Default
    @Schema(description = "Platos base disponibles")
    private List<MenuItemResponse> plates = new ArrayList<>();

    @Builder.Default
    @Schema(description = "Ingredientes extras disponibles")
    private List<MenuItemResponse> extras = new ArrayList<>();

    @Builder.Default
    @Schema(description = "Métodos de entrega soportados")
    private List<String> deliveryTypes = new ArrayList<>();

    @Schema(description = "Cantidad máxima de platos por pedido", example = "5")
    private int maxPlatesPerOrder;

    @Schema(description = "Monto mínimo del pedido en COP", example = "3500")
    private int minOrderPrice;
}
