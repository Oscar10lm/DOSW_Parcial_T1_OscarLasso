package edu.dosw.parcial.controller;

import edu.dosw.parcial.controller.dtos.request.CreateOrderRequest;
import edu.dosw.parcial.controller.dtos.request.UpdateStatusRequest;
import edu.dosw.parcial.controller.dtos.response.OrderResponse;
import edu.dosw.parcial.controller.mappers.OrderDtoMapper;
import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderStatus;
import edu.dosw.parcial.core.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Operaciones para la creación y gestión del ciclo de vida de pedidos")
public class OrderController {

    private final OrderService orderService;
    private final OrderDtoMapper orderDtoMapper;

    @PostMapping
    @Operation(summary = "Crear nuevo pedido", description = "Construye un pedido validando reglas de negocio con el patrón Builder y cálculo dinámico de precios con Decorator.")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order created = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDtoMapper.toResponse(created));
    }

    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Obtiene todos los pedidos registrados o los filtra opcionalmente por estado.")
    public ResponseEntity<List<OrderResponse>> getOrders(@RequestParam(required = false) OrderStatus status) {
        List<Order> orders = (status != null)
            ? orderService.getOrdersByStatus(status)
            : orderService.getAllOrders();

        List<OrderResponse> responses = orders.stream()
            .map(orderDtoMapper::toResponse)
            .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna el detalle completo de un pedido según su identificador en MongoDB.")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(orderDtoMapper.toResponse(order));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Actualizar estado de pedido", description = "Modifica el estado de un pedido (PENDIENTE -> EN_PREPARACION -> LISTO -> ENTREGADO / CANCELADO).")
    public ResponseEntity<OrderResponse> updateStatus(
        @PathVariable String id,
        @Valid @RequestBody UpdateStatusRequest request
    ) {
        Order updated = orderService.updateOrderStatus(id, request.getStatus());
        return ResponseEntity.ok(orderDtoMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar pedido", description = "Elimina un pedido existente por su identificador.")
    public ResponseEntity<Void> deleteOrder(@PathVariable String id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
