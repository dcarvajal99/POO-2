package com.grupo7.gestioninventariojava.view;

import java.util.List;
import java.util.Scanner;

import com.grupo7.gestioninventariojava.controller.InventarioController;
import com.grupo7.gestioninventariojava.model.Producto;

/**
 * Vista principal que gestiona la interfaz de usuario del sistema de inventario
 * Implementa el patrón MVC como componente View
 * 
 *@author grupo7
 */
public class MenuPrincipal {
    private final InventarioController controller;
    private final Scanner scanner;
    
    /**
     * Constructor que inicializa la vista con un controlador
     */
    public MenuPrincipal() {
        this.controller = new InventarioController();
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Constructor que permite inyectar un controlador específico
     * @param controller Controlador a usar
     */
    public MenuPrincipal(InventarioController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }
    
    /**
     * Método principal que ejecuta el menú
     */
    public void ejecutar() {
        mostrarBienvenida();
        
        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = leerOpcion();
            continuar = procesarOpcion(opcion);
        }
        
        mostrarDespedida();
        scanner.close();
    }
    
    /**
     * Muestra el mensaje de bienvenida
     */
    private void mostrarBienvenida() {
        System.out.println("=== SISTEMA DE GESTIÓN DE INVENTARIO ===");
        System.out.println("Bienvenido al sistema de gestión de inventario");
    }
    
    /**
     * Muestra el mensaje de despedida
     */
    private void mostrarDespedida() {
        System.out.println("¡Gracias por usar el sistema de gestión de inventario!");
    }
    
    /**
     * Muestra el menú principal de opciones
     */
    private void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Agregar producto");
        System.out.println("2. Eliminar producto");
        System.out.println("3. Actualizar producto");
        System.out.println("4. Buscar productos");
        System.out.println("5. Listar todos los productos");
        System.out.println("6. Mostrar resumen del inventario");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opción (1-7): ");
    }
    
    /**
     * Lee la opción seleccionada por el usuario
     * @return Opción seleccionada
     */
    private int leerOpcion() {
        try {
            int opcion = Integer.parseInt(scanner.nextLine().trim());
            return opcion;
        } catch (NumberFormatException e) {
            mostrarError("Por favor ingrese un número válido.");
            return -1;
        }
    }
    
    /**
     * Procesa la opción seleccionada por el usuario
     * @param opcion Opción seleccionada
     * @return true si debe continuar, false si debe salir
     */
    private boolean procesarOpcion(int opcion) {
        return switch (opcion) {
            case 1 -> {
                agregarProducto();
                yield true;
            }
            case 2 -> {
                eliminarProducto();
                yield true;
            }
            case 3 -> {
                actualizarProducto();
                yield true;
            }
            case 4 -> {
                buscarProductos();
                yield true;
            }
            case 5 -> {
                listarProductos();
                yield true;
            }
            case 6 -> {
                mostrarResumen();
                yield true;
            }
            case 7 -> false;
            default -> {
                mostrarError("Opción inválida. Por favor seleccione una opción del 1 al 7.");
                yield true;
            }
        };
    }
    
    /**
     * Permite al usuario agregar un nuevo producto
     */
    private void agregarProducto() {
        System.out.println("\n--- AGREGAR PRODUCTO ---");
        
        String nombre = leerNombre();
        if (nombre == null) return;
        
        String descripcion = leerDescripcion();
        double precio = leerPrecio();
        if (precio < 0) return;
        
        int cantidad = leerCantidad();
        if (cantidad < 0) return;
        
        InventarioController.ResultadoOperacion resultado = controller.agregarProducto(nombre, descripcion, precio, cantidad);
        mostrarResultado(resultado);
    }
    
    /**
     * Permite al usuario eliminar un producto
     */
    private void eliminarProducto() {
        System.out.println("\n--- ELIMINAR PRODUCTO ---");
        
        if (controller.estaInventarioVacio()) {
            mostrarInfo("El inventario está vacío. No hay productos para eliminar.");
            return;
        }
        
        int id = leerId();
        if (id <= 0) return;
        
        InventarioController.ResultadoOperacion resultado = controller.eliminarProducto(id);
        mostrarResultado(resultado);
    }
    
    /**
     * Permite al usuario actualizar un producto existente
     */
    private void actualizarProducto() {
        System.out.println("\n--- ACTUALIZAR PRODUCTO ---");
        
        if (controller.estaInventarioVacio()) {
            mostrarInfo("El inventario está vacío. No hay productos para actualizar.");
            return;
        }
        
        int id = leerId();
        if (id <= 0) return;
        
        Producto productoExistente = controller.buscarProductoPorId(id);
        if (productoExistente == null) {
            mostrarError("No se encontró un producto con el ID " + id);
            return;
        }
        
        mostrarInfo("Producto actual: " + productoExistente);
        System.out.println("\nIngrese los nuevos datos:");
        
        String nuevoNombre = leerNombre();
        if (nuevoNombre == null) return;
        
        String nuevaDescripcion = leerDescripcion();
        double nuevoPrecio = leerPrecio();
        if (nuevoPrecio < 0) return;
        
        int nuevaCantidad = leerCantidad();
        if (nuevaCantidad < 0) return;
        
        InventarioController.ResultadoOperacion resultado = controller.actualizarProducto(id, nuevoNombre, nuevaDescripcion, nuevoPrecio, nuevaCantidad);
        mostrarResultado(resultado);
    }
    
    /**
     * Permite al usuario buscar productos
     */
    private void buscarProductos() {
        System.out.println("\n--- BUSCAR PRODUCTOS ---");
        
        if (controller.estaInventarioVacio()) {
            mostrarInfo("El inventario está vacío. No hay productos para buscar.");
            return;
        }
        
        System.out.print("Ingrese el criterio de búsqueda (nombre o descripción): ");
        String criterio = scanner.nextLine().trim();
        
        if (criterio.isEmpty()) {
            mostrarError("El criterio de búsqueda no puede estar vacío.");
            return;
        }
        
        List<Producto> resultados = controller.buscarProductos(criterio);
        mostrarResultadosBusqueda(resultados, criterio);
    }
    
    /**
     * Lista todos los productos del inventario
     */
    private void listarProductos() {
        System.out.println("\n--- LISTA DE PRODUCTOS ---");
        
        if (controller.estaInventarioVacio()) {
            mostrarInfo("El inventario está vacío.");
            return;
        }
        
        List<Producto> productos = controller.obtenerTodosLosProductos();
        mostrarListaProductos(productos);
    }
    
    /**
     * Muestra un resumen del inventario
     */
    private void mostrarResumen() {
        System.out.println("\n--- RESUMEN DEL INVENTARIO ---");
        
        InventarioController.ResumenInventario resumen = controller.obtenerResumenInventario();
        mostrarResumenInventario(resumen);
    }
    
    /**
     * Lee el nombre del producto
     * @return Nombre leído o null si hay error
     */
    private String leerNombre() {
        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine().trim();
        
        if (nombre.isEmpty()) {
            mostrarError("El nombre no puede estar vacío.");
            return null;
        }
        
        return nombre;
    }
    
    /**
     * Lee la descripción del producto
     * @return Descripción leída
     */
    private String leerDescripcion() {
        System.out.print("Ingrese la descripción del producto: ");
        return scanner.nextLine().trim();
    }
    
    /**
     * Lee el precio del producto
     * @return Precio leído o -1 si hay error
     */
    private double leerPrecio() {
        System.out.print("Ingrese el precio del producto: ");
        try {
            double precio = Double.parseDouble(scanner.nextLine().trim());
            if (precio < 0) {
                mostrarError("El precio no puede ser negativo.");
                return -1;
            }
            return precio;
        } catch (NumberFormatException e) {
            mostrarError("Por favor ingrese un número decimal válido.");
            return -1;
        }
    }
    
    /**
     * Lee la cantidad en stock
     * @return Cantidad leída o -1 si hay error
     */
    private int leerCantidad() {
        System.out.print("Ingrese la cantidad en stock: ");
        try {
            int cantidad = Integer.parseInt(scanner.nextLine().trim());
            if (cantidad < 0) {
                mostrarError("La cantidad no puede ser negativa.");
                return -1;
            }
            return cantidad;
        } catch (NumberFormatException e) {
            mostrarError("Por favor ingrese un número entero válido.");
            return -1;
        }
    }
    
    /**
     * Lee el ID del producto
     * @return ID leído o -1 si hay error
     */
    private int leerId() {
        System.out.print("Ingrese el ID del producto: ");
        try {
            int id = Integer.parseInt(scanner.nextLine().trim());
            if (id <= 0) {
                mostrarError("El ID debe ser un número positivo.");
                return -1;
            }
            return id;
        } catch (NumberFormatException e) {
            mostrarError("Por favor ingrese un número entero válido.");
            return -1;
        }
    }
    
    /**
     * Muestra un mensaje de error
     * @param mensaje Mensaje de error
     */
    private void mostrarError(String mensaje) {
        System.err.println("Error: " + mensaje);
    }
    
    /**
     * Muestra un mensaje informativo
     * @param mensaje Mensaje informativo
     */
    private void mostrarInfo(String mensaje) {
        System.out.println(mensaje);
    }
    
    /**
     * Muestra el resultado de una operación
     * @param resultado Resultado de la operación
     */
    private void mostrarResultado(InventarioController.ResultadoOperacion resultado) {
        if (resultado.isExito()) {
            System.out.println("✓ " + resultado.getMensaje());
        } else {
            mostrarError(resultado.getMensaje());
        }
    }
    
    /**
     * Muestra los resultados de una búsqueda
     * @param resultados Lista de productos encontrados
     * @param criterio Criterio de búsqueda usado
     */
    private void mostrarResultadosBusqueda(List<Producto> resultados, String criterio) {
        if (resultados.isEmpty()) {
            mostrarInfo("No se encontraron productos que coincidan con el criterio: " + criterio);
        } else {
            System.out.println("Productos encontrados (" + resultados.size() + "):");
            for (Producto producto : resultados) {
                System.out.println("- " + producto);
            }
        }
    }
    
    /**
     * Muestra una lista de productos
     * @param productos Lista de productos a mostrar
     */
    private void mostrarListaProductos(List<Producto> productos) {
        System.out.println("Total de productos: " + productos.size());
        System.out.println();
        
        for (Producto producto : productos) {
            System.out.println(producto);
        }
    }
    
    /**
     * Muestra el resumen del inventario
     * @param resumen Resumen del inventario
     */
    private void mostrarResumenInventario(InventarioController.ResumenInventario resumen) {
        System.out.println("Total de productos: " + resumen.getTotalProductos());
        System.out.println("Valor total del inventario: $" + String.format("%.2f", resumen.getValorTotal()));
        System.out.println("Total de unidades en stock: " + resumen.getTotalStock());
    }
}

