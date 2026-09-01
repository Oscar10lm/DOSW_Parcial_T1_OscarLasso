package edu.dosw.parcial.universityFoodHub;

import java.util.ArrayList;
import java.util.List;

public class ProductBuilder {
    private Product product;
    private Product extra;

    public void bandejaUniversitaria () {this.product = new Product("Bandeja Universitaria", ".", 8_500);
    }
    public void sandwich  () {this.product = new Plate("Sandwich", 5_000);}
    public void ensalada () {this.product = new Plate("Ensalada", 4_500);}
    public void sopaDelDia () {this.product = new Plate("Sopa del día",  3_500);}
    public void jugoNatural () {this.product = new Plate("Jugo natural",  2_500);}

    public void porcionProteina () {this.extra = new Plate("Porción extra de proteína ",  2_500);}
    public void aguacate () {this.extra = new Plate("Adición de aguacate ",  2_500);}
    public void panIntegral () {this.extra = new Plate("Cambio a pan integral  ",  2_500);}

    public void AdicionQueso () {this.extra = new Plate("Cambio a pan integral  ",  2_500);}

    public void BebidaAdicional () {this.extra = new Plate("Cambio a pan integral  ",  2_500);}





    public Product build() thorws ProductException {
        if (product == null)  throw new ProductException(ProductException.PRODUCT_REQUIRED);

        List<Plate> plates = new ArrayList<>();
        plates.add(product);

        if (extra != null)       plates.add(extra);

        return new Product(plates);

    }


}
