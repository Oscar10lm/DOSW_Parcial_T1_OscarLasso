package edu.dosw.parcial.persistence;

import edu.dosw.parcial.core.models.Order;
import edu.dosw.parcial.core.models.OrderItem;
import edu.dosw.parcial.core.models.OrderStatus;
import edu.dosw.parcial.persistence.entities.OrderDocument;
import edu.dosw.parcial.persistence.entities.OrderItemDocument;
import edu.dosw.parcial.persistence.mappers.OrderPersistenceMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderPersistenceMapperTest {

    private OrderPersistenceMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new OrderPersistenceMapper();
    }

    @Test
    @DisplayName("Debe mapear de modelo de dominio a documento MongoDB")
    void shouldMapDomainToDocument() {
        Order domain = Order.builder()
            .id("abc-123")
            .customerName("Oscar Lasso")
            .deliveryType("Local")
            .totalPrice(8500)
            .status(OrderStatus.PENDIENTE)
            .createdAt(LocalDateTime.now())
            .items(List.of(
                OrderItem.builder()
                    .plateName("Bandeja Universitaria")
                    .basePrice(8500)
                    .extras(List.of())
                    .totalPrice(8500)
                    .build()
            ))
            .build();

        OrderDocument doc = mapper.toDocument(domain);

        assertNotNull(doc);
        assertEquals("abc-123", doc.getId());
        assertEquals("Oscar Lasso", doc.getCustomerName());
        assertEquals("PENDIENTE", doc.getStatus());
        assertEquals(1, doc.getItems().size());
        assertEquals("Bandeja Universitaria", doc.getItems().get(0).getPlateName());
    }

    @Test
    @DisplayName("Debe mapear de documento MongoDB a modelo de dominio")
    void shouldMapDocumentToDomain() {
        OrderDocument doc = OrderDocument.builder()
            .id("xyz-789")
            .customerName("Oscar Lasso")
            .deliveryType("Salón")
            .totalPrice(10000)
            .status("EN_PREPARACION")
            .createdAt(LocalDateTime.now())
            .items(List.of(
                OrderItemDocument.builder()
                    .plateName("Sandwich")
                    .basePrice(5000)
                    .extras(List.of("Porción extra de proteína", "Adición de queso"))
                    .totalPrice(9500)
                    .build()
            ))
            .build();

        Order domain = mapper.toDomain(doc);

        assertNotNull(domain);
        assertEquals("xyz-789", domain.getId());
        assertEquals(OrderStatus.EN_PREPARACION, domain.getStatus());
        assertEquals(1, domain.getItems().size());
        assertEquals(2, domain.getItems().get(0).getExtras().size());
    }

    @Test
    @DisplayName("Debe manejar valores nulos de forma segura")
    void shouldHandleNulls() {
        assertNull(mapper.toDocument(null));
        assertNull(mapper.toDomain(null));
        assertNull(mapper.toItemDocument(null));
        assertNull(mapper.toItemDomain(null));
    }
}
