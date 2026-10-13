package edu.dosw.parcial.core.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public final class Plate implements Consumible {
    private final String name;
    private final int price;

    public Plate(String name, int price) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del plato no puede estar vacío");
        }
        if (price < 0) {
            throw new IllegalArgumentException("El precio del plato no puede ser negativo");
        }
        this.name = name;
        this.price = price;
    }
}
