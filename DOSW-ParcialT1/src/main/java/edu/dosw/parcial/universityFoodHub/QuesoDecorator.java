package edu.dosw.parcial.universityFoodHub;

public class QuesoDecorator extends ExtraDecorator {
    public QuesoDecorator(Consumible wrapped) {
        super(wrapped);
    }

    @Override
    public String getName() {
        return wrapped.getName() + " + Queso";
    }

    @Override
    public int getPrice() {
        return wrapped.getPrice() + 2000;
    }
}
