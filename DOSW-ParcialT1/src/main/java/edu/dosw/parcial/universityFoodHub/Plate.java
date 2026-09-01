package edu.dosw.parcial.universityFoodHub;

public final class Plate {
    private final String name;
    private final int price;

    public Plate(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {return name;}
    public int getPrice() {return price;}

}
