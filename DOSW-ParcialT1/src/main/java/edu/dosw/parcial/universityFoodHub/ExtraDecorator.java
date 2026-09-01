package edu.dosw.parcial.universityFoodHub;

public abstract class ExtraDecorator implements Consumible {
    protected Consumible wrapped;

    public ExtraDecorator(Consumible wrapped) {
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
