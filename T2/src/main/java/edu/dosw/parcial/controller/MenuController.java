package edu.dosw.parcial.controller;

import edu.dosw.parcial.controller.dtos.response.MenuCatalogResponse;
import edu.dosw.parcial.controller.dtos.response.MenuItemResponse;
import edu.dosw.parcial.core.utils.MenuCatalog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/menu")
@Tag(name = "Menú", description = "Catálogo de platos base, ingredientes extras y opciones de entrega")
public class MenuController {

    @GetMapping
    @Operation(summary = "Consultar menú completo", description = "Retorna todos los platos base, adiciones extras y métodos de entrega disponibles")
    public ResponseEntity<MenuCatalogResponse> getMenu() {
        List<MenuItemResponse> plates = new ArrayList<>();
        MenuCatalog.getPlates().forEach((name, price) ->
            plates.add(MenuItemResponse.builder().name(name).price(price).type("PLATO").build())
        );

        List<MenuItemResponse> extras = new ArrayList<>();
        MenuCatalog.getExtras().forEach((name, price) ->
            extras.add(MenuItemResponse.builder().name(name).price(price).type("EXTRA").build())
        );

        MenuCatalogResponse response = MenuCatalogResponse.builder()
            .plates(plates)
            .extras(extras)
            .deliveryTypes(new ArrayList<>(MenuCatalog.getDeliveryTypes()))
            .maxPlatesPerOrder(MenuCatalog.MAX_PLATES_PER_ORDER)
            .minOrderPrice(MenuCatalog.MIN_ORDER_PRICE)
            .build();

        return ResponseEntity.ok(response);
    }
}
