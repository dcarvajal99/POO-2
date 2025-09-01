# Proyecto Ecommerce (Java)

Este proyecto es una simulación de un sistema de ecommerce desarrollado en Java, orientado a la venta de productos con gestión de carrito, descuentos y emisión de boletas. El diseño sigue el patrón MVC y utiliza los patrones Command y Decorator para la lógica de negocio y descuentos.

## Estructura del Proyecto

```
src/main/java/com/grupo7/ecommerce/
├── App.java                # Punto de entrada principal
├── controller/             # Controladores (lógica de negocio)
├── model/                  # Modelos de dominio, DTOs y decoradores
│   ├── decorator/          # Decoradores para descuentos
│   └── dto/                # Objetos de transferencia de datos (Boleta, Línea)
├── command/                # Comandos (patrón Command)
├── repository/             # Acceso a datos (CSV, etc.)
├── ui/                     # Interfaz de usuario (consola)
├── utils/                  # Utilidades generales
├── view/                   # Vistas (presentación)
├── data/                   # Archivos de datos (CSV)
```

## Patrones de Diseño Utilizados

- **MVC (Modelo-Vista-Controlador):**
  - `model/`: Entidades, DTOs y decoradores.
  - `controller/`: Controladores que gestionan la lógica de negocio.
  - `view/`: Vistas para mostrar información al usuario.
  - `ui/`: Interfaz de usuario (consola).
- **Command:**
  - `command/`: Permite encapsular acciones como agregar/eliminar productos y configurar descuentos.
- **Decorator:**
  - `model/decorator/`: Permite aplicar descuentos de forma flexible y encadenada a los productos.

## Instalación y Ejecución

1. **Requisitos:**
   - Java 11 o superior
   - Maven (opcional, si deseas compilar con Maven)

2. **Compilación:**
   - Desde la raíz del proyecto, ejecuta:
     ```bash
     javac -d target/classes src/main/java/com/grupo7/ecommerce/**/*.java
     ```
   - O usando Maven:
     ```bash
     mvn clean package
     ```

3. **Ejecución:**
   - Desde la raíz del proyecto, ejecuta:
     ```bash
     java -cp target/classes com.grupo7.ecommerce.App
     ```

## Funcionalidades Principales

- **Catálogo de productos:** Visualización y gestión de productos desde archivo CSV.
- **Carrito de compras:** Agregar y eliminar productos, ver resumen y total.
- **Descuentos:**
  - Descuento global configurable.
  - Descuento por categoría en promoción.
  - Descuento por cupón válido.
- **Emisión de boleta:** Genera boleta con detalle de productos, descuentos y ahorro total.
- **Persistencia:** Estado del carrito y boletas se guardan en archivos CSV.

## Créditos y Contacto

- Proyecto desarrollado por Grupo 7 para el curso de Programación Orientada a Objetos II (Duoc UC).
- Contacto: Diego Carvajal

---

¡Gracias por revisar este proyecto! Si tienes dudas o sugerencias, no dudes en contactarnos.
