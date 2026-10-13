package edu.dosw.parcial.core.models;

public abstract class ExtraDecorator implements Consumible {
    protected final Consumible wrapped;

    public ExtraDecorator(Consumible wrapped) {
        if (wrapped == null) {
            throw new IllegalArgumentException("El consumible a decorar no puede ser nulo");
        }
        this.wrapped = wrapped;
    }

    @Override
    public String getName() {
        return wrapped.getName();
    }

    @Override
    public int getPrice() {
        return wrapped.getPrice();
    }
}
