package edu.dosw.parcial.universityFoodHub;

import java.util.ArrayList;
import java.util.List;

public class ProductBuilder {
    private List<Consumible> plates = new ArrayList<>();
    private Consumible currentPlate;
    private String tipoEntrega;

    private void saveCurrentPlate() {
        if (currentPlate != null) {
            if (plates.size() < 5) {
                plates.add(currentPlate);
            }
            currentPlate = null;
        }
    }


    public ProductBuilder bandejaUniversitaria() {
        saveCurrentPlate();
        this.currentPlate = new Plate("Bandeja Universitaria", 8500);
        return this;
    }
    public ProductBuilder sandwich() {
        saveCurrentPlate();
        this.currentPlate = new Plate("Sandwich", 5000);
        return this;
    }
    public ProductBuilder ensalada() {
        saveCurrentPlate();
        this.currentPlate = new Plate("Ensalada", 4500);
        return this;
    }
    public ProductBuilder sopaDelDia() {
        saveCurrentPlate();
        this.currentPlate = new Plate("Sopa del día", 3500);
        return this;
    }
    public ProductBuilder jugoNatural() {
        saveCurrentPlate();
        this.currentPlate = new Plate("Jugo natural", 2500);
        return this;
    }


    public ProductBuilder porcionProteina() {
        if (currentPlate != null) {
            currentPlate = new GenericExtraDecorator(currentPlate, "Porción extra de proteína", 2500);
        }
        return this;
    }
    public ProductBuilder aguacate() {
        if (currentPlate != null) {
            currentPlate = new GenericExtraDecorator(currentPlate, "Adición de aguacate", 2500);
        }
        return this;
    }
    public ProductBuilder panIntegral() {
        if (currentPlate != null) {
            currentPlate = new GenericExtraDecorator(currentPlate, "Cambio a pan integral", 2500);
        }
        return this;
    }
    public ProductBuilder AdicionQueso() {
        if (currentPlate != null) {
            currentPlate = new QuesoDecorator(currentPlate);
        }
        return this;
    }
    public ProductBuilder BebidaAdicional() {
        if (currentPlate != null) {
            currentPlate = new GenericExtraDecorator(currentPlate, "Bebida adicional", 2500);
        }
        return this;
    }


    public ProductBuilder setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
        return this;
    }

    public Product build() throws ProductException {
        saveCurrentPlate();

        if (plates.isEmpty()) {
            throw new ProductException(ProductException.PRODUCT_REQUIRED);
        }
        if (tipoEntrega == null || tipoEntrega.isEmpty()) {
            throw new RuntimeException("El método de entrega es obligatorio.");
        }
        Product product = new Product(plates, tipoEntrega);
        
        if (product.getTotalPrice() < 3500) {
            throw new RuntimeException("El pedido mínimo es de $3500.");
        }
        
        return product;
    }
}
