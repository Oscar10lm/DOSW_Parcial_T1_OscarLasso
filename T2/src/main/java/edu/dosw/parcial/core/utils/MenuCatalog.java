package edu.dosw.parcial.core.utils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class MenuCatalog {

    public static final int MAX_PLATES_PER_ORDER = 5;
    public static final int MIN_ORDER_PRICE = 3500;

    private static final Map<String, Integer> PLATES = new LinkedHashMap<>();
    private static final Map<String, Integer> EXTRAS = new LinkedHashMap<>();
    private static final Set<String> DELIVERY_TYPES = Set.of("Para llevar", "Local", "Salón");

    static {
        PLATES.put("Bandeja Universitaria", 8500);
        PLATES.put("Sandwich", 5000);
        PLATES.put("Ensalada", 4500);
        PLATES.put("Sopa del día", 3500);
        PLATES.put("Jugo natural", 2500);

        EXTRAS.put("Porción extra de proteína", 2500);
        EXTRAS.put("Adición de aguacate", 2500);
        EXTRAS.put("Cambio a pan integral", 2500);
        EXTRAS.put("Adición de queso", 2000);
        EXTRAS.put("Bebida adicional", 2500);
    }

    private MenuCatalog() {
    }

    public static Map<String, Integer> getPlates() {
        return Collections.unmodifiableMap(PLATES);
    }

    public static Map<String, Integer> getExtras() {
        return Collections.unmodifiableMap(EXTRAS);
    }

    public static Set<String> getDeliveryTypes() {
        return Collections.unmodifiableSet(DELIVERY_TYPES);
    }

    public static boolean isValidPlate(String plateName) {
        return plateName != null && PLATES.containsKey(plateName);
    }

    public static Integer getPlatePrice(String plateName) {
        return PLATES.get(plateName);
    }

    public static boolean isValidExtra(String extraName) {
        return extraName != null && EXTRAS.containsKey(extraName);
    }

    public static Integer getExtraPrice(String extraName) {
        return EXTRAS.get(extraName);
    }

    public static boolean isValidDeliveryType(String type) {
        return type != null && DELIVERY_TYPES.contains(type);
    }
}
