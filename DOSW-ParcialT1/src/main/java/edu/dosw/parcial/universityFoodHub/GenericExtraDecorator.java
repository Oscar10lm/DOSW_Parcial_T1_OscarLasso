package edu.dosw.parcial.universityFoodHub;

public class GenericExtraDecorator extends ExtraDecorator {
    private final String extraName;
    private final int extraPrice;

    public GenericExtraDecorator(Consumible wrapped, String extraName, int extraPrice) {
        super(wrapped);
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
