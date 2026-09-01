package edu.dosw.parcial.universityFoodHub;

public class ProductException extends Exception {

    public static final String PRODUCT_REQUIRED = "Se requiere un producto para su plato.";
    public static final String INVALID_OPTION = "Opción inválida. Debe seleccionar s o n.";
    public ProductException(String message) {
        super(message);
    }
}
