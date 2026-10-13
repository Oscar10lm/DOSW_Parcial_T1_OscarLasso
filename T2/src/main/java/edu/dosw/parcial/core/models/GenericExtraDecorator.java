package edu.dosw.parcial.core.models;

import lombok.Getter;

@Getter
public class GenericExtraDecorator extends ExtraDecorator {
    private final String extraName;
    private final int extraPrice;

    public GenericExtraDecorator(Consumible wrapped, String extraName, int extraPrice) {
        super(wrapped);
        if (extraName == null || extraName.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del extra no puede estar vacío");
        }
        if (extraPrice < 0) {
            throw new IllegalArgumentException("El precio del extra no puede ser negativo");
        }
        this.extraName = extraName;
        this.extraPrice = extraPrice;
    }

    @Override
    public String getName() {
        return wrapped.getName() + " + " + extraName;
    }

    @Override
    public int getPrice() {
        return wrapped.getPrice() + extraPrice;
    }
}
