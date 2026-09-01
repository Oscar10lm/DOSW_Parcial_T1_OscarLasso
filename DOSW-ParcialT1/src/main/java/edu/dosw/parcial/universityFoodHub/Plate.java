package edu.dosw.parcial.universityFoodHub;

public final class Plate implements Consumible {
    private final String name;
    private final int price;

    public Plate(String name, int price) {
        this.name = name;
        this.price = price;
    }
    
    @Override
    public String getName() {return name;}
    
    @Override
    public int getPrice() {return price;}
}
