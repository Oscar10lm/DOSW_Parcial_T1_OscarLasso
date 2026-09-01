package edu.dosw.parcial.universityFoodHub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Product {

    private final List<Consumible> plates;
    private final String tipoEntrega;
    
    Product(List<Consumible> plates, String tipoEntrega) {
        this.plates = Collections.unmodifiableList(new ArrayList<>(plates));
        this.tipoEntrega = tipoEntrega;
    }
    
    public List<Consumible> getPlates() { return plates; }
    
    public String getTipoEntrega() { return tipoEntrega; }
    
    public int getTotalPrice() {
        return plates.stream().mapToInt(Consumible::getPrice).sum();
    }
    
    public void display() {
        System.out.println("=== RESUMEN DEL PEDIDO ===");
        System.out.println("Método de entrega: " + tipoEntrega);
        System.out.println("Platos:");
        for (Consumible plate : plates) {
            System.out.println(" - " + plate.getName() + " ($" + plate.getPrice() + ")");
        }
        System.out.println("TOTAL A PAGAR: $" + getTotalPrice());
        System.out.println("==========================");
    }
}
