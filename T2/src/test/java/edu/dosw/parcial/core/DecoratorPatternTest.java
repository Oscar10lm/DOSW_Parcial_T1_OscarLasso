package edu.dosw.parcial.core;

import edu.dosw.parcial.core.models.Consumible;
import edu.dosw.parcial.core.models.GenericExtraDecorator;
import edu.dosw.parcial.core.models.Plate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DecoratorPatternTest {

    @Test
    @DisplayName("Debe instanciar plato base con precio y nombre correctos")
    void shouldCreateBasePlate() {
        Consumible sandwich = new Plate("Sandwich", 5000);
        assertEquals("Sandwich", sandwich.getName());
        assertEquals(5000, sandwich.getPrice());
    }

    @Test
    @DisplayName("Debe decorar un plato con una adición sumando precio y nombre")
    void shouldDecorateWithSingleExtra() {
        Consumible sandwich = new Plate("Sandwich", 5000);
        Consumible conQueso = new GenericExtraDecorator(sandwich, "Adición de queso", 2000);

        assertEquals("Sandwich + Adición de queso", conQueso.getName());
        assertEquals(7000, conQueso.getPrice());
    }

    @Test
    @DisplayName("Debe encadenar múltiples decoradores dinámicamente")
    void shouldDecorateWithMultipleExtras() {
        Consumible bandeja = new Plate("Bandeja Universitaria", 8500);
        Consumible conProteina = new GenericExtraDecorator(bandeja, "Porción extra de proteína", 2500);
        Consumible conAguacate = new GenericExtraDecorator(conProteina, "Adición de aguacate", 2500);

        assertEquals("Bandeja Universitaria + Porción extra de proteína + Adición de aguacate", conAguacate.getName());
        assertEquals(13500, conAguacate.getPrice());
    }

    @Test
    @DisplayName("Debe lanzar excepción si el nombre o precio son inválidos")
    void shouldThrowExceptionForInvalidPlate() {
        assertThrows(IllegalArgumentException.class, () -> new Plate("", 1000));
        assertThrows(IllegalArgumentException.class, () -> new Plate("Sopa", -500));
    }
}
