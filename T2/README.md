# DOSW_ParcialT2_OscarLasso — UNIVERSITY FOOD HUB (Continuación T1 en T2)

## Índice
1. [Datos del estudiante](#1-datos-del-estudiante)
2. [Descripción del proyecto y continuidad con T1](#2-descripción-del-proyecto-y-continuidad-con-t1)
3. [Estructura del proyecto](#3-estructura-del-proyecto)
4. [Patrones de diseño aplicados](#4-patrones-de-diseño-aplicados)
5. [Endpoints de la API REST](#5-endpoints-de-la-api-rest)
6. [Tecnologías y dependencias](#6-tecnologías-y-dependencias)
7. [Configuración y variables de entorno](#7-configuración-y-variables-de-entorno)
8. [Cómo ejecutar](#8-cómo-ejecutar)
9. [Pruebas automatizadas y cobertura](#9-pruebas-automatizadas-y-cobertura)
10. [Docker y contenedorización](#10-docker-y-contenedorización)
11. [Pipeline CI/CD](#11-pipeline-cicd)
12. [Herramientas de modelado y diseño (evidencias)](#12-herramientas-de-modelado-y-diseño-evidencias)

---

## 1. Datos del estudiante
- **Nombre completo:** Oscar David Lasso Martinez
- **Materia:** Diseño y Optimización de Software (DOSW)
- **Grupo:** DOSW - 01RE
- **Profesor:** Daniel Benitez

---

## 2. Descripción del proyecto y continuidad con T1

En el **Parcial T1**, se diseñó y modeló el dominio de **UNIVERSITY FOOD HUB**, enfocado en la lógica pura de construcción de pedidos de cafetería mediante patrones **Builder** y **Decorator**:
- **Builder (`OrderBuilder`):** Permite armar pedidos paso a paso con las restricciones del negocio: entre 1 y 5 platos por pedido, método de entrega obligatorio ("Para llevar", "Local", "Salón") y monto mínimo de compra ($3.500 COP).
- **Decorator (`ExtraDecorator`, `GenericExtraDecorator`):** Permite añadir dinámicamente ingredientes y extras a cada plato base (proteína extra, aguacate, pan integral, queso, bebida adicional) calculando el valor total de manera desacoplada y abierta a extensión (OCP).

En este **Parcial T2**, se realiza la **continuación e integración integral hacia una arquitectura de microservicio web empresarial** en **Spring Boot 3.3.4 (Java 21)** con base de datos NoSQL **MongoDB**:
1. Exposición de API RESTful con controladores, DTOs, validaciones `jakarta.validation` y documentación interactiva con **OpenAPI / Swagger**.
2. Separación de responsabilidades mediante arquitectura limpia en capas (`controller`, `core`, `persistence`, `config`, `main`).
3. Mapeo desacoplado DTO ↔ Dominio ↔ Documento MongoDB (`OrderDtoMapper`, `OrderPersistenceMapper`).
4. Manejo centralizado de excepciones con `@RestControllerAdvice` y respuestas consistentes (`ErrorResponse`).
5. Persistencia real en colección `orders` de MongoDB mediante `OrderRepository`.
6. Pruebas unitarias y de integración completas (31 pruebas automatizadas pasando al 100%) con **JUnit 5**, **Mockito** y **MockMvc**, con reporte de cobertura **JaCoCo**.
7. Contenedorización multietapa con **Docker** y pipeline automatizado con **GitHub Actions**.

---

## 3. Estructura del proyecto

El proyecto sigue rigurosamente el diseño en capas especificado para el Parcial T2:

```text
src/main/java/edu/dosw/parcial/
├── config/        # Configuraciones globales de Spring Boot
│   ├── MongoConfig.java          # Habilitación de repositorios de MongoDB
│   ├── OpenApiConfig.java        # Configuración Swagger / OpenAPI v3
│   └── SecurityConfig.java       # Seguridad Stateless con endpoints permitidos
├── controller/    # Capa de entrada: endpoints REST, DTOs, mappers y handlers
│   ├── dtos/
│   │   ├── request/              # DTOs de entrada (CreateOrderRequest, OrderPlateRequest, UpdateStatusRequest)
│   │   └── response/             # DTOs de salida (OrderResponse, OrderPlateResponse, MenuCatalogResponse, ErrorResponse)
│   ├── handlers/
│   │   └── GlobalExceptionHandler.java # Captura global de excepciones (400, 404, 500)
│   ├── mappers/
│   │   └── OrderDtoMapper.java   # Mapper IN: Dominio <-> DTO
│   ├── HealthController.java     # Endpoint /api/v1/health
│   ├── MenuController.java       # Endpoint /api/v1/menu
│   └── OrderController.java      # Endpoints CRUD /api/v1/orders
├── core/          # Lógica central del negocio (independiente de infraestructura)
│   ├── models/                   # Modelos de dominio y patrones
│   │   ├── Consumible.java       # Interfaz base del Decorator
│   │   ├── Plate.java            # Componente concreto (plato base)
│   │   ├── ExtraDecorator.java   # Decorador base abstracto
│   │   ├── GenericExtraDecorator.java # Decorador concreto de ingredientes
│   │   ├── Order.java            # Entidad de dominio puro del Pedido
│   │   ├── OrderItem.java        # Plato configurado dentro del pedido
│   │   ├── OrderStatus.java      # Enum (PENDIENTE, EN_PREPARACION, LISTO, ENTREGADO, CANCELADO)
│   │   └── OrderBuilder.java     # Patrón Builder con validaciones de negocio
│   ├── services/                 # Casos de uso
│   │   ├── OrderService.java     # Contrato del servicio
│   │   └── impl/OrderServiceImpl.java # Implementación del servicio y orquestación
│   ├── utils/
│   │   └── MenuCatalog.java      # Catálogo y constantes del menú oficial
│   └── validators/
│       ├── BusinessRuleException.java    # Excepción para violaciones de reglas
│       ├── ResourceNotFoundException.java# Excepción para 404
│       └── OrderValidator.java   # Validaciones de transiciones de estado y pedidos
├── persistence/   # Infraestructura de acceso a datos (MongoDB)
│   ├── entities/
│   │   ├── OrderDocument.java    # Colección @Document("orders")
│   │   └── OrderItemDocument.java# Documento embebido para platos del pedido
│   ├── mappers/
│   │   └── OrderPersistenceMapper.java # Mapper OUT: Dominio <-> Documento MongoDB
│   └── repositories/
│       └── OrderRepository.java  # Interfaz MongoRepository<OrderDocument, String>
└── main/
    └── ParcialApplication.java   # Clase principal con @SpringBootApplication
```

---

## 4. Patrones de diseño aplicados

### 4.1 Builder (`OrderBuilder`)
- **Tipo:** Creacional.
- **Propósito:** Construye el objeto compuesto `Order` paso a paso garantizando que ningún pedido se cree en un estado inconsistente.
- **Validaciones integradas:**
  - Máximo 5 platos por pedido.
  - Al menos 1 plato requerido.
  - Método de entrega obligatorio y validado contra las opciones autorizadas (`Para llevar`, `Local`, `Salón`).
  - Total mínimo de compra de $3.500 COP.

### 4.2 Decorator (`ExtraDecorator`, `GenericExtraDecorator`)
- **Tipo:** Estructural.
- **Propósito:** Permite asociar cualquier combinación de extras (adición de queso, proteína extra, aguacate, pan integral, bebida adicional) a cualquier plato base (`Plate`) sin generar una explosión de clases hijas.
- **Cálculo dinámico:** Cada decorador delega al elemento envuelto y añade su propio costo y descripción en tiempo de ejecución.

### 4.3 Data Transfer Object (DTO) y Mapper Pattern
- Separa los contratos de entrada/salida HTTP (`CreateOrderRequest`, `OrderResponse`) de las entidades del dominio puro (`Order`), evitando acoplamientos y fugas de datos.

### 4.4 Repository Pattern (`OrderRepository`)
- Abstrae el mecanismo de persistencia sobre MongoDB usando Spring Data, permitiendo cambiar o consultar la base de datos sin afectar las reglas de negocio.

---

## 5. Endpoints de la API REST

Base URL: `http://localhost:8080/api/v1`  
Swagger UI: `http://localhost:8080/swagger-ui/index.html`  
OpenAPI Docs: `http://localhost:8080/v3/api-docs`

| Método | Endpoint | Descripción | Código Éxito |
| :--- | :--- | :--- | :--- |
| `GET` | `/api/v1/health` | Estado del microservicio | `200 OK` |
| `GET` | `/api/v1/menu` | Catálogo de platos, extras y métodos de entrega | `200 OK` |
| `POST` | `/api/v1/orders` | Construir y confirmar un nuevo pedido | `201 Created` |
| `GET` | `/api/v1/orders` | Listar pedidos (filtro opcional `?status=...`) | `200 OK` |
| `GET` | `/api/v1/orders/{id}` | Consultar detalle de un pedido por ID | `200 OK` |
| `PATCH` | `/api/v1/orders/{id}/status` | Cambiar estado del pedido | `200 OK` |
| `DELETE` | `/api/v1/orders/{id}` | Eliminar o cancelar un pedido | `204 No Content` |

### Ejemplo: Crear Pedido (`POST /api/v1/orders`)

**Body (JSON):**
```json
{
  "customerName": "Oscar Lasso",
  "deliveryType": "Local",
  "plates": [
    {
      "plateName": "Sandwich",
      "extras": [
        "Porción extra de proteína",
        "Adición de queso"
      ]
    },
    {
      "plateName": "Jugo natural",
      "extras": []
    }
  ]
}
```

**Respuesta (`201 Created`):**
```json
{
  "id": "670c2f81498b3c10a1b2c3d4",
  "customerName": "Oscar Lasso",
  "deliveryType": "Local",
  "plates": [
    {
      "plateName": "Sandwich",
      "basePrice": 5000,
      "extras": [
        "Porción extra de proteína",
        "Adición de queso"
      ],
      "totalPrice": 9500
    },
    {
      "plateName": "Jugo natural",
      "basePrice": 2500,
      "extras": [],
      "totalPrice": 2500
    }
  ],
  "totalPrice": 12000,
  "status": "PENDIENTE",
  "createdAt": "2026-10-13T12:30:00"
}
```

---

## 6. Tecnologías y dependencias

| Tecnología | Versión | Uso en el proyecto |
| :--- | :--- | :--- |
| **Java** | 21 (Temurin LTS) | Lenguaje base con records, pattern matching y virtual threads |
| **Spring Boot** | 3.3.4 | Framework base para el desarrollo del backend |
| **Spring Web** | 3.3.4 | Controladores REST y manejo de peticiones HTTP |
| **Spring Data MongoDB** | 3.3.4 | Mapeo objeto-documento para bases de datos NoSQL |
| **Spring Validation** | 3.3.4 | Validaciones declarativas (`@NotBlank`, `@Size`, `@NotEmpty`) |
| **Spring Security** | 3.3.4 | Seguridad Stateless y protección de rutas |
| **SpringDoc OpenAPI** | 2.5.0 | Generación interactiva de documentación Swagger |
| **Lombok** | 1.18.34 | Reducción de boilerplate (`@Getter`, `@Builder`, `@Data`) |
| **JUnit 5 & Mockito** | 3.3.4 | Suite de pruebas unitarias y mocks en memoria |
| **JaCoCo** | 0.8.12 | Medición y reporte de cobertura de código |

---

## 7. Configuración y variables de entorno

| Variable | Descripción | Valor por defecto / Ejemplo |
| :--- | :--- | :--- |
| `MONGODB_URI` | URI de conexión a la base de datos MongoDB | `mongodb://localhost:27017/dosw_parcial` |
| `PORT` | Puerto de escucha del servicio | `8080` |
| `JWT_SECRET` | Llave secreta para firma de tokens JWT | `dGhpc2lzYXNlY3JldGtleXRoYXRtdXN0YmVhdGxlYXN0NDRjaGFyc2xvbmc=` |

---

## 8. Cómo ejecutar

### 8.1 Local con Maven
```bash
# Desde la carpeta T2:
mvn spring-boot:run
```

### 8.2 Levantar MongoDB local con Docker
```bash
docker run -d -p 27017:27017 --name mongo-foodhub mongo:7
```

### 8.3 Conectar a Mongo Atlas
```bash
export MONGODB_URI="mongodb+srv://<usuario>:<password>@cluster0.mongodb.net/dosw_foodhub?retryWrites=true&w=majority"
mvn spring-boot:run
```

---

## 9. Pruebas automatizadas y cobertura

Para ejecutar la suite completa de pruebas unitarias y de integración:
```bash
mvn test
```

### Resultados de la suite:
- **Total de pruebas:** 31
- **Fallos:** 0
- **Errores:** 0
- **Ignoradas:** 0
- **Estado:** `BUILD SUCCESS` (100% verde)

Clases probadas:
- `HealthControllerTest`: Verificación de endpoint health.
- `MenuControllerTest`: Verificación de catálogo y restricciones expuestas.
- `OrderControllerTest`: Endpoints CRUD, validaciones 400 y errores 404.
- `DecoratorPatternTest`: Decoración individual y múltiple de platos con extras.
- `OrderBuilderTest`: Validaciones del Builder (límite de 5 platos, monto mínimo $3500, método de entrega).
- `OrderServiceTest`: Casos de uso orquestados con mocks de Mockito.
- `OrderValidatorTest`: Validaciones de negocio y transiciones de estado.
- `OrderPersistenceMapperTest`: Mapeos seguros bidireccionales Dominio ↔ MongoDB.

Reporte generado por JaCoCo en: `target/site/jacoco/index.html`.

---

## 10. Docker y contenedorización

El proyecto cuenta con un `Dockerfile` multietapa optimizado:

```bash
# Construir la imagen Docker:
docker build -t dosw-parcial-t2:latest .

# Ejecutar el contenedor:
docker run -p 8080:8080 -e MONGODB_URI=mongodb://host.docker.internal:27017/dosw_parcial dosw-parcial-t2:latest
```

---

## 11. Pipeline CI/CD

El flujo automatizado de integración continua se encuentra en `.github/workflows/ci-cd.yml` y ejecuta:
1. **Paso de Test:** Compilación en Java 21 Temurin y ejecución de las 31 pruebas con JaCoCo.
2. **Paso de Docker:** Construcción de imagen Docker multietapa y publicación en Docker Hub.
3. **Paso de Deploy:** Despliegue automático en Azure App Service.

---

## 12. Herramientas de modelado y diseño (evidencias)

Las evidencias de diseño y modelado se encuentran en:
- `docs/images/drawio.png`: Diagrama de arquitectura y flujo.
- `docs/images/figma.png`: Mockup y diseño UI.
- `docs/uml/`: Diagramas UML de clases y secuencia.
