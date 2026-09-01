# Requerimientos del Sistema - University Food Hub

A continuación se presentan los 5 requerimientos funcionales y no funcionales, alineados con el uso de los patrones Builder y Decorator.

## Requerimientos Funcionales

*   **RF1: Construir un pedido complejo seleccionando producto base, extras y tipo de entrega**
    *   *Descripción:* El sistema permite construir un pedido secuencialmente agregando productos base, extras y la preferencia de entrega.
    *   *Justificación:* Encaja perfectamente con el patrón **Builder**, ya que un pedido es un objeto complejo que requiere ensamblarse paso a paso.

*   **RF2: Calcular el precio final del producto sumando dinámicamente todos los costos del pedido**
    *   *Descripción:* El sistema permite personalizar los productos agregando extras y calcula el precio sumando el costo base y los adicionales de forma dinámica.
    *   *Justificación:* Se alinea con el patrón **Decorator**, el cual permite añadir responsabilidades (como el costo de los extras) a un objeto base en tiempo de ejecución.

*   **RF3: Registrar los pedidos confirmados de la cafetería en la base de datos AWS Mongo Atlas**
    *   *Descripción:* El sistema debe asegurar que una vez finalizado y confirmado el pedido, la información sea persistida correctamente en la base de datos de Mongo Atlas.

## Requerimientos No Funcionales

*   **RNF1: Mostrar la interfaz web usando tipografía Poppins y Colores Azul y Dorado**
    *   *Descripción:* Se debe garantizar la identidad visual de la cafetería implementando la paleta corporativa y tipografía exigidas en la interfaz de usuario.

*   **RNF2: Responder a las peticiones del sistema en 1.5 segundos o menos para el 90% de los casos**
    *   *Descripción:* El sistema debe mantener un rendimiento óptimo de baja latencia incluso soportando la carga máxima esperada de 300 pedidos simultáneos.
