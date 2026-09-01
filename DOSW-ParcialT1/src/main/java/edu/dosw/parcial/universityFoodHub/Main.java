package edu.dosw.parcial.universityFoodHub;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("EVALUACIÓN DE ESCENARIOS\n");

        caso1_PedidoBasicoConExtras();
        caso2_PedidoComplejoVariosPlatos();
        caso3_FalloPorNoDefinirEntrega();
        caso4_FalloPorPedidoMinimo();
        
        System.out.println("\nFin");
    }

    private static void caso1_PedidoBasicoConExtras() {
        System.out.println("caso 1: pedido basico con decorator");
        try {
            ProductBuilder builder = new ProductBuilder();
            Product pedido = builder
                .sandwich()
                .porcionProteina()
                .AdicionQueso()
                .setTipoEntrega("Local")
                .build();
            pedido.display();
        } catch (ProductException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void caso2_PedidoComplejoVariosPlatos() {
        System.out.println("\ncaso 2: pedido complejo con multiples platos");
        try {
            ProductBuilder builder = new ProductBuilder();
            Product pedido = builder
                .bandejaUniversitaria()
                .BebidaAdicional()
                .ensalada()
                .aguacate()
                .panIntegral()
                .setTipoEntrega("Para llevar")
                .build();
            pedido.display();
        } catch (ProductException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void caso3_FalloPorNoDefinirEntrega() {
        System.out.println("\ncaso 3: falla por no definir metodo de entrega");
        try {
            ProductBuilder builder = new ProductBuilder();
            builder.sandwich().AdicionQueso().build();
            System.out.println("MAL: El pedido se creo sin metodo de entrega.");
        } catch (Exception e) {
            System.out.println("BIEN - Se bloqueo la creacion: " + e.getMessage());
        }
    }

    private static void caso4_FalloPorPedidoMinimo() {
        System.out.println("\ncaso 4: falla por no alcanzar pedido minimo");
        try {
            ProductBuilder builder = new ProductBuilder();
            builder.jugoNatural()
                   .setTipoEntrega("Salon")
                   .build();
            System.out.println("MAL: El pedido paso con menos de 3500.");
        } catch (Exception e) {
            System.out.println("BIEN - Se bloqueo la creacion: " + e.getMessage());
        }
    }
}

