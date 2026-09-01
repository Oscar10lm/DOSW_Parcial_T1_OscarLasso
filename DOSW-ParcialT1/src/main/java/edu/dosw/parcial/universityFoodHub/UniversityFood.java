package edu.dosw.parcial.universityFoodHub;

import java.util.Scanner;

public class UniversityFood {
    private final Scanner scanner;
    public UniversityFood(Scanner scanner) {
        this.scanner = scanner;
    }

    public Product makeProduct() throws ProductException {
        System.out.println("Bienvenido a UNIVERSITY FOOD HUB");
        ProductBuilder builder = new ProductBuilder();
        
        boolean agregandoPlatos = true;
        int platosAgregados = 0;
        
        while (agregandoPlatos && platosAgregados < 5) {
            System.out.println("\nArma tu plato (Plato #" + (platosAgregados + 1) + " de máximo 5):");
            System.out.print("  ¿Plato? (1-Bandeja universitaria / 2-Sandwich / 3-Ensalada / 4-Sopa del dia / 5-JugoNatural): ");
            String product = scanner.nextLine().trim();
            
            if (product.equals("1")) builder.bandejaUniversitaria();
            else if (product.equals("2")) builder.sandwich();
            else if (product.equals("3")) builder.ensalada();
            else if (product.equals("4")) builder.sopaDelDia();
            else if (product.equals("5")) builder.jugoNatural();
            else throw new ProductException(ProductException.PRODUCT_REQUIRED);

            System.out.print("  ¿Porción extra de proteína? (s-Sí $2,500 / n-No) -> ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) builder.porcionProteina();

            System.out.print("  ¿Adición de aguacate? (s-Sí $2,500 / n-No) -> ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) builder.aguacate();

            System.out.print("  ¿Cambio a pan integral? (s-Sí $2,500 / n-No) -> ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) builder.panIntegral();

            System.out.print("  ¿Adición de queso? (s-Sí $2,000 / n-No) -> ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) builder.AdicionQueso();

            System.out.print("  ¿Bebida adicional? (s-Sí $2,500 / n-No) -> ");
            if (scanner.nextLine().trim().equalsIgnoreCase("s")) builder.BebidaAdicional();

            platosAgregados++;
            
            if (platosAgregados < 5) {
                System.out.print("¿Deseas agregar otro plato al pedido? (s-Sí / n-No) -> ");
                if (!scanner.nextLine().trim().equalsIgnoreCase("s")) {
                    agregandoPlatos = false;
                }
            } else {
                System.out.println("Has alcanzado el límite máximo de 5 platos por pedido.");
            }
        }
        
        System.out.println("\n--- Preferencia de Entrega ---");
        System.out.print("Selecciona un método de entrega (1-Para llevar / 2-Local / 3-Salón): ");
        String entrega = scanner.nextLine().trim();
        if (entrega.equals("1")) builder.setTipoEntrega("Para llevar");
        else if (entrega.equals("2")) builder.setTipoEntrega("Local");
        else if (entrega.equals("3")) builder.setTipoEntrega("Salón");
        else builder.setTipoEntrega("No especificado (Requerido)");

        return builder.build();
    }
}
