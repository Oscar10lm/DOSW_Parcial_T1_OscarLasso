package edu.dosw.parcial.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.parcial.controller.dtos.request.CreateOrderRequest;
import edu.dosw.parcial.controller.dtos.request.OrderPlateRequest;
import edu.dosw.parcial.controller.dtos.request.UpdateStatusRequest;
import edu.dosw.parcial.controller.dtos.response.OrderPlateResponse;
import edu.dosw.parcial.controller.dtos.response.OrderResponse;
import edu.dosw.parcial.controller.handlers.GlobalExceptionHandler;
import edu.dosw.parcial.controller.mappers.OrderDtoMapper;
import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderStatus;
import edu.dosw.parcial.core.services.OrderService;
import edu.dosw.parcial.core.validators.ResourceNotFoundException;
import edu.dosw.parcial.main.ParcialApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
@ContextConfiguration(classes = ParcialApplication.class)
@Import(GlobalExceptionHandler.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderDtoMapper orderDtoMapper;

    @Test
    @DisplayName("Debe crear un pedido exitosamente y retornar HTTP 201")
    void shouldCreateOrderSuccessfully() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
            .customerName("Oscar Lasso")
            .deliveryType("Local")
            .plates(List.of(
                OrderPlateRequest.builder()
                    .plateName("Sandwich")
                    .extras(List.of("Adición de queso"))
                    .build()
            ))
            .build();

        Order domainOrder = Order.builder().id("123").customerName("Oscar Lasso").totalPrice(7000).build();
        OrderResponse response = OrderResponse.builder()
            .id("123")
            .customerName("Oscar Lasso")
            .deliveryType("Local")
            .plates(List.of(
                OrderPlateResponse.builder().plateName("Sandwich").basePrice(5000).totalPrice(7000).build()
            ))
            .totalPrice(7000)
            .status("PENDIENTE")
            .createdAt(LocalDateTime.now())
            .build();

        when(orderService.createOrder(any())).thenReturn(domainOrder);
        when(orderDtoMapper.toResponse(domainOrder)).thenReturn(response);

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value("123"))
            .andExpect(jsonPath("$.customerName").value("Oscar Lasso"))
            .andExpect(jsonPath("$.totalPrice").value(7000));
    }

    @Test
    @DisplayName("Debe retornar HTTP 400 cuando los campos obligatorios están ausentes")
    void shouldReturnBadRequestWhenFieldsAreMissing() throws Exception {
        CreateOrderRequest request = CreateOrderRequest.builder()
            .customerName("")
            .deliveryType("")
            .plates(List.of())
            .build();

        mockMvc.perform(post("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error").value("Error de Validación de Entrada"));
    }

    @Test
    @DisplayName("Debe obtener un pedido existente por ID")
    void shouldGetOrderById() throws Exception {
        Order domainOrder = Order.builder().id("123").customerName("Oscar Lasso").totalPrice(7000).build();
        OrderResponse response = OrderResponse.builder().id("123").customerName("Oscar Lasso").build();

        when(orderService.getOrderById("123")).thenReturn(domainOrder);
        when(orderDtoMapper.toResponse(domainOrder)).thenReturn(response);

        mockMvc.perform(get("/api/v1/orders/123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("123"));
    }

    @Test
    @DisplayName("Debe retornar HTTP 404 si el pedido no existe")
    void shouldReturnNotFoundWhenOrderDoesNotExist() throws Exception {
        when(orderService.getOrderById("999"))
            .thenThrow(new ResourceNotFoundException("Pedido no encontrado con ID: 999"));

        mockMvc.perform(get("/api/v1/orders/999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("Recurso No Encontrado"))
            .andExpect(jsonPath("$.message").value("Pedido no encontrado con ID: 999"));
    }

    @Test
    @DisplayName("Debe actualizar el estado de un pedido")
    void shouldUpdateOrderStatus() throws Exception {
        UpdateStatusRequest request = UpdateStatusRequest.builder()
            .status(OrderStatus.EN_PREPARACION)
            .build();

        Order updatedOrder = Order.builder().id("123").status(OrderStatus.EN_PREPARACION).build();
        OrderResponse response = OrderResponse.builder().id("123").status("EN_PREPARACION").build();

        when(orderService.updateOrderStatus(eq("123"), eq(OrderStatus.EN_PREPARACION))).thenReturn(updatedOrder);
        when(orderDtoMapper.toResponse(updatedOrder)).thenReturn(response);

        mockMvc.perform(patch("/api/v1/orders/123/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status").value("EN_PREPARACION"));
    }

    @Test
    @DisplayName("Debe eliminar un pedido y retornar HTTP 204")
    void shouldDeleteOrder() throws Exception {
        doNothing().when(orderService).deleteOrder("123");

        mockMvc.perform(delete("/api/v1/orders/123"))
            .andExpect(status().isNoContent());

        verify(orderService, times(1)).deleteOrder("123");
    }
}
