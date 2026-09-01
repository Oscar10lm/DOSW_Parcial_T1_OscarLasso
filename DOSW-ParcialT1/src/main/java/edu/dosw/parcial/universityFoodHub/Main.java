package edu.dosw.parcial.universityFoodHub;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){run(new Scanner(System.in));}
    public static void run() {
        run(new Scanner(System.in));
    }

    public static void run(Scanner scanner){
        try {
            UniversityFood universityFood = new UniversityFood(scanner);
            Product product = universityFood.makeProduct();
            product.display();
        }catch(ProductException e){

        }

    }

}
