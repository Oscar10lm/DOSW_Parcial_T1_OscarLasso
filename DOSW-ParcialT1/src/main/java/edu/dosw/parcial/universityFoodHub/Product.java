package edu.dosw.parcial.universityFoodHub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Product {

    private final List<Plate> plates;
    Product(List<Plate> plates) {
        this.plates = Collections.unmodifiableList(new ArrayList<>(plates));
    }
    public List<Plate> getPlates(){return plates;}
    public int getTotalPrice(){
        return plates.stream().mapToInt(Plate::getPrice).sum();
    }

}

