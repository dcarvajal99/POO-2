package com.grupo7.gestioninventariojava.controller;

import java.util.List;

import com.grupo7.gestioninventariojava.model.Inventario;
import com.grupo7.gestioninventariojava.model.Producto;

/**
 * Controlador que maneja la lógica de negocio del sistema de inventario
 * Actúa como intermediario entre la vista y el modelo
 * 
 *@author grupo7
 */
public class InventarioController {
    private final Inventario inventario;
    
    /**
     * Constructor que inicializa el controlador con un inventario
     */
    public InventarioController() {
        this.inventario = new Inventario();
    }
    
    /**
     * Constructor que permite inyectar un inventario específico
     * @param inventario Inventario a usar
     */
    public InventarioController(Inventario inventario) {
        this.inventario = inventario;
    }
    
    /**
     * Agrega un nuevo producto al inventario
     * @param nombre Nombre del producto
     * @param descripcion Descripción del producto
     * @param precio Precio del producto
     * @param cantidadStock Cantidad en stock
     * @return Resultado de la operación con mensaje
     */
    public ResultadoOperacion agregarProducto(String nombre, String descripcion, double precio, int cantidadStock) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ResultadoOperacion(false, "El nombre del producto no puede estar vacío.");
        }
        try {
            Producto producto = new Producto(0, nombre.trim(), descripcion != null ? descripcion.trim() : "", precio, cantidadStock);
            boolean exito = inventario.agregarProducto(producto);
            if (exito) {
                return new ResultadoOperacion(true, "Producto agregado exitosamente: " + producto.getNombre());
            } else {
                return new ResultadoOperacion(false, "Error al agregar el producto.");
            }
        } catch (IllegalArgumentException e) {
            return new ResultadoOperacion(false, e.getMessage());
        }
    }
    
    /**
     * Elimina un producto del inventario
     * @param id ID del producto a eliminar
     * @return Resultado de la operación con mensaje
     */
    public ResultadoOperacion eliminarProducto(int id) {
        if (id <= 0) {
            return new ResultadoOperacion(false, "El ID debe ser un número positivo.");
        }
        
        Producto producto = inventario.buscarPorId(id);
        if (producto == null) {
            return new ResultadoOperacion(false, "No se encontró un producto con el ID " + id);
        }
        
        boolean exito = inventario.eliminarProducto(id);
        if (exito) {
            return new ResultadoOperacion(true, "Producto eliminado exitosamente: " + producto.getNombre());
        } else {
            return new ResultadoOperacion(false, "Error al eliminar el producto.");
        }
    }
    
    /**
     * Actualiza un producto existente
     * @param id ID del producto a actualizar
     * @param nuevoNombre Nuevo nombre del producto
     * @param nuevaDescripcion Nueva descripción del producto
     * @param nuevoPrecio Nuevo precio del producto
     * @param nuevaCantidad Nueva cantidad en stock
     * @return Resultado de la operación con mensaje
     */
    public ResultadoOperacion actualizarProducto(int id, String nuevoNombre, String nuevaDescripcion, 
                                                double nuevoPrecio, int nuevaCantidad) {
        if (id <= 0) {
            return new ResultadoOperacion(false, "El ID debe ser un número positivo.");
        }
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            return new ResultadoOperacion(false, "El nombre del producto no puede estar vacío.");
        }
        Producto productoExistente = inventario.buscarPorId(id);
        if (productoExistente == null) {
            return new ResultadoOperacion(false, "No se encontró un producto con el ID " + id);
        }
        try {
            boolean exito = inventario.actualizarProducto(id, nuevoNombre.trim(),
                                                        nuevaDescripcion != null ? nuevaDescripcion.trim() : "",
                                                        nuevoPrecio, nuevaCantidad);
            if (exito) {
                return new ResultadoOperacion(true, "Producto actualizado exitosamente: " + nuevoNombre);
            } else {
                return new ResultadoOperacion(false, "Error al actualizar el producto.");
            }
        } catch (IllegalArgumentException e) {
            return new ResultadoOperacion(false, e.getMessage());
        }
    }
    
    /**
     * Busca productos por criterio
     * @param criterio Criterio de búsqueda
     * @return Lista de productos encontrados
     */
    public List<Producto> buscarProductos(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return List.of();
        }
        return inventario.buscarProductos(criterio.trim());
    }
    
    /**
     * Busca un producto por ID
     * @param id ID del producto a buscar
     * @return Producto encontrado o null
     */
    public Producto buscarProductoPorId(int id) {
        return inventario.buscarPorId(id);
    }
    
    /**
     * Obtiene todos los productos del inventario
     * @return Lista de todos los productos
     */
    public List<Producto> obtenerTodosLosProductos() {
        return inventario.listarTodosLosProductos();
    }
    
    /**
     * Obtiene el número total de productos
     * @return Cantidad total de productos
     */
    public int obtenerTotalProductos() {
        return inventario.obtenerTotalProductos();
    }
    
    /**
     * Verifica si el inventario está vacío
     * @return true si está vacío, false si tiene productos
     */
    public boolean estaInventarioVacio() {
        return inventario.estaVacio();
    }
    
    /**
     * Obtiene el siguiente ID disponible
     * @return Siguiente ID a asignar
     */
    public int obtenerSiguienteId() {
        return inventario.obtenerSiguienteId();
    }
    
    /**
     * Obtiene un resumen del inventario
     * @return Resumen del inventario
     */
    public ResumenInventario obtenerResumenInventario() {
        List<Producto> productos = inventario.listarTodosLosProductos();
        
        int totalProductos = productos.size();
        double valorTotal = productos.stream()
                .mapToDouble(p -> p.getPrecio() * p.getCantidadStock())
                .sum();
        
        int totalStock = productos.stream()
                .mapToInt(Producto::getCantidadStock)
                .sum();
        
        return new ResumenInventario(totalProductos, valorTotal, totalStock);
    }
    
    /**
     * Clase interna para representar el resultado de una operación
     */
    public static class ResultadoOperacion {
        private final boolean exito;
        private final String mensaje;
        
        public ResultadoOperacion(boolean exito, String mensaje) {
            this.exito = exito;
            this.mensaje = mensaje;
        }
        
        public boolean isExito() {
            return exito;
        }
        
        public String getMensaje() {
            return mensaje;
        }
    }
    
    /**
     * Clase interna para representar el resumen del inventario
     */
    public static class ResumenInventario {
        private final int totalProductos;
        private final double valorTotal;
        private final int totalStock;
        
        public ResumenInventario(int totalProductos, double valorTotal, int totalStock) {
            this.totalProductos = totalProductos;
            this.valorTotal = valorTotal;
            this.totalStock = totalStock;
        }
        
        public int getTotalProductos() {
            return totalProductos;
        }
        
        public double getValorTotal() {
            return valorTotal;
        }
        
        public int getTotalStock() {
            return totalStock;
        }
    }
}
