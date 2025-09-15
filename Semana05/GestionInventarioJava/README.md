# Sistema de Gestión de Inventario Java

## Descripción

Aplicación de gestión de inventario desarrollada en Java que implementa la arquitectura MVC (Model-View-Controller). Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre productos, con una interfaz de usuario basada en consola.

## Arquitectura MVC

El sistema utiliza el patrón arquitectónico MVC para separar las responsabilidades:

- **Model**: Maneja los datos y la lógica de negocio (`Producto`, `Inventario`)
- **View**: Gestiona la interfaz de usuario (`MenuPrincipal`)
- **Controller**: Coordina entre la vista y el modelo (`InventarioController`)

## Características

- ✅ Arquitectura MVC implementada
- ✅ Gestión completa de productos (agregar, eliminar, actualizar, buscar)
- ✅ Interfaz de usuario intuitiva con menú interactivo
- ✅ Búsqueda por nombre y descripción
- ✅ Validación de datos de entrada en todas las capas
- ✅ Manejo de errores robusto
- ✅ Pruebas unitarias y de integración completas por capa MVC
- ✅ Documentación técnica detallada

## Requisitos del Sistema

- Java 21 o superior
- Maven 3.6 o superior
- Apache NetBeans (recomendado)

## Estructura del Proyecto MVC

```
GestionInventarioJava/
├── src/
│   ├── main/java/com/grupo7/gestioninventariojava/
│   │   ├── GestionInventarioJava.java    # Clase principal
│   │   ├── model/                        # Capa Model
│   │   │   ├── Producto.java             # Modelo de datos
│   │   │   └── Inventario.java           # Lógica de negocio
│   │   ├── view/                         # Capa View
│   │   │   └── MenuPrincipal.java        # Interfaz de usuario
│   │   └── controller/                   # Capa Controller
│   │       └── InventarioController.java # Lógica de control
│   └── test/java/com/grupo7/gestioninventariojava/
│       ├── model/                        # Pruebas Model Layer
│       │   ├── ProductoTest.java          # Pruebas unitarias Producto
│       │   └── InventarioTest.java       # Pruebas unitarias Inventario
│       └── controller/                   # Pruebas Controller Layer
│           ├── InventarioControllerTest.java      # Pruebas unitarias Controller
│           └── PruebasIntegracionMVCTest.java     # Pruebas de integración MVC
├── pom.xml                               # Configuración Maven
├── DOCUMENTACION.md                      # Documentación técnica
└── README.md                             # Este archivo
```

## Instalación y Ejecución

### 1. Clonar o descargar el proyecto

### 2. Compilar el proyecto
```bash
mvn clean compile
```

### 3. Ejecutar las pruebas
```bash
mvn test
```

### 4. Ejecutar la aplicación
```bash
mvn exec:java -Dexec.mainClass="com.grupo7.gestioninventariojava.GestionInventarioJava"
```

## Uso de la Aplicación

Al ejecutar la aplicación, se mostrará un menú con las siguientes opciones:

1. **Agregar producto**: Permite agregar nuevos productos al inventario
2. **Eliminar producto**: Elimina productos por ID
3. **Actualizar producto**: Modifica información de productos existentes
4. **Buscar productos**: Busca productos por nombre o descripción
5. **Listar todos los productos**: Muestra todos los productos del inventario
6. **Mostrar resumen del inventario**: Muestra estadísticas del inventario
7. **Salir**: Termina la aplicación

## Pruebas

El proyecto incluye un conjunto completo de pruebas organizadas por capa MVC:

- **65 pruebas totales** ejecutadas exitosamente
- **37 pruebas Model Layer** para las clases Producto e Inventario
- **21 pruebas Controller Layer** para la lógica de control
- **7 pruebas de integración MVC** para verificar la interacción entre componentes
- **100% de cobertura** de funcionalidades principales

### Ejecutar pruebas por capa
```bash
# Solo pruebas del Model Layer
mvn test -Dtest="com.grupo7.gestioninventariojava.model.*"

# Solo pruebas del Controller Layer
mvn test -Dtest="com.grupo7.gestioninventariojava.controller.*"

# Solo pruebas de integración MVC
mvn test -Dtest="PruebasIntegracionMVCTest"
```

## Arquitectura MVC

### Model Layer
**Producto**: Representa los items individuales del inventario con atributos:
- ID único
- Nombre
- Descripción
- Precio
- Cantidad en stock

**Inventario**: Gestiona la colección de productos con métodos para:
- Agregar productos
- Eliminar productos
- Actualizar productos
- Buscar productos
- Listar productos

### View Layer
**MenuPrincipal**: Proporciona la interfaz de usuario con:
- Menú interactivo
- Validación de entrada
- Manejo de errores
- Formateo de salida

### Controller Layer
**InventarioController**: Coordina entre vista y modelo con:
- Validación de datos de entrada
- Lógica de negocio
- Manejo de errores estructurado
- Respuestas con mensajes descriptivos

## Cumplimiento de Requisitos

### Requisitos Funcionales
- ✅ Agregar productos al inventario
- ✅ Eliminar productos del inventario
- ✅ Actualizar productos por nombre y descripción
- ✅ Buscar productos por nombre o descripción
- ✅ Listar todos los productos
- ✅ Interfaz de usuario interactiva

### Requisitos No Funcionales
- ✅ Interfaz de consola clara e intuitiva
- ✅ Operaciones eficientes de búsqueda y listado
- ✅ Tiempo de respuesta rápido
- ✅ Sistema modular y escalable
- ✅ Manejo de errores para datos inválidos

## Tecnologías Utilizadas

- **Java 21**: Lenguaje de programación
- **Maven**: Gestión de dependencias
- **JUnit 5**: Framework de pruebas
- **Apache NetBeans**: IDE de desarrollo

## Contribución

Este proyecto fue desarrollado como parte de una actividad formativa del curso de Programación Orientada a Objetos 2.

## Licencia

Este proyecto es parte de una actividad académica y está destinado únicamente para fines educativos.

## Contacto

Desarrollado por: Diego Carvajal Arias
Curso: Programación Orientada a Objetos 2
Institución: Duoc UC
