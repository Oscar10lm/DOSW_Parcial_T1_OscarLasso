package edu.dosw.parcial.universityFoodHub;

import java.util.Scanner;

public class UniversityFood {
    private final Scanner scanner;
    public UniversityFood(Scanner scanner) {
        this.scanner = scanner;
    }

    public Product makeProduct()throws ProductException {
        System.out.println("Bienvenido a UNIVERSITY FOOD HUB");
        System.out.println("Arma tu plato:\n");

        ProductBuilder builder = new ProductBuilder();

        System.out.print("  ¿Plato? (1-Bandeja universitaria  / 2-Sandwich / 3-Ensalada / 4-Sopa del dia /5-JugoNatural");
        String product = scanner.nextLine().trim();
        if (product.equals("1")) {
            builder.bandejaUniversitaria();
        } else if (product.equals("2")) {
            builder.sandwich();
        } else if (product.equals("3")) {
            builder.ensalada();
        } else if (product.equals("4")) {
            builder.sopaDelDia();
        } else if (product.equals("5")) {
            builder.jugoNatural();
        } else {
            throw new ProductException(ProductException.PRODUCT_REQUIRED);
        }

        System.out.print("  ¿Porción extra de proteína ? (s-Sí $2,000 / n-No) -> ");
        String protein = scanner.nextLine().trim();
        if (protein.equalsIgnoreCase("s")) {
            builder.porcionProteina();
        } else if (!protein.equalsIgnoreCase("n")) {
            throw new ProductException(ProductException.INVALID_OPTION);
        }

        System.out.print("  ¿Adición de aguacate  ? (s-Sí $2,000 / n-No) -> ");
        String aguacate = scanner.nextLine().trim();
        if (aguacate.equalsIgnoreCase("s")) {
            builder.aguacate();
        } else if (!aguacate.equalsIgnoreCase("n")) {
            throw new ProductException(ProductException.INVALID_OPTION);
        }

        System.out.print("  ¿Cambio a pan integral  ? (s-Sí $2,000 / n-No) -> ");
        String pan = scanner.nextLine().trim();
        if (pan.equalsIgnoreCase("s")) {
            builder.panIntegral();
        } else if (!pan.equalsIgnoreCase("n")) {
            throw new ProductException(ProductException.INVALID_OPTION);
        }

        System.out.print("  ¿Adición de queso ? (s-Sí $2,000 / n-No) -> ");
        String queso = scanner.nextLine().trim();
        if (queso.equalsIgnoreCase("s")) {
            builder.AdicionQueso();
        } else if (!queso.equalsIgnoreCase("n")) {
            throw new ProductException(ProductException.INVALID_OPTION);
        }

        System.out.print("  ¿Bebida adicional  ? (s-Sí $2,000 / n-No) -> ");
        String bebida = scanner.nextLine().trim();
        if (bebida.equalsIgnoreCase("s")) {
            builder.BebidaAdicional();
        } else if (!bebida.equalsIgnoreCase("n")) {
            throw new ProductException(ProductException.INVALID_OPTION);
        }

        return builder.build();
    }

