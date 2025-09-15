package com.grupo7.gestioninventariojava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase Inventario que gestiona una colección de productos
 * Incluye métodos para agregar, eliminar, buscar y listar productos
 * 
 *@author grupo7
 */
public class Inventario {
    private final List<Producto> productos;
    private int siguienteId;
    
    /**
     * Constructor que inicializa el inventario vacío
     */
    public Inventario() {
        this.productos = new ArrayList<>();
        this.siguienteId = 1;
    }
    
    /**
     * Agrega un nuevo producto al inventario
     * @param producto Producto a agregar
     * @return true si se agregó correctamente, false si el producto es nulo
     */
    public boolean agregarProducto(Producto producto) {
        if (producto == null) {
            System.err.println("Error: No se puede agregar un producto nulo.");
            return false;
        }
        
        // Asignar ID automático si no tiene uno
        if (producto.getId() == 0) {
            producto.setId(siguienteId);
            siguienteId++;
        }
        
        // Verificar si ya existe un producto con el mismo ID
        if (buscarPorId(producto.getId()) != null) {
            System.err.println("Error: Ya existe un producto con el ID " + producto.getId());
            return false;
        }
        
        productos.add(producto);
        System.out.println("Producto agregado exitosamente: " + producto.getNombre());
        return true;
    }
    
    /**
     * Elimina un producto del inventario por su ID
     * @param id ID del producto a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    public boolean eliminarProducto(int id) {
        Producto producto = buscarPorId(id);
        if (producto != null) {
            productos.remove(producto);
            System.out.println("Producto eliminado exitosamente: " + producto.getNombre());
            return true;
        } else {
            System.err.println("Error: No se encontró un producto con el ID " + id);
            return false;
        }
    }
    
    /**
     * Actualiza un producto existente por su ID
     * @param id ID del producto a actualizar
     * @param nuevoNombre Nuevo nombre del producto
     * @param nuevaDescripcion Nueva descripción del producto
     * @param nuevoPrecio Nuevo precio del producto
     * @param nuevaCantidad Nueva cantidad en stock
     * @return true si se actualizó correctamente, false si no se encontró
     */
    public boolean actualizarProducto(int id, String nuevoNombre, String nuevaDescripcion, 
                                   double nuevoPrecio, int nuevaCantidad) {
        Producto producto = buscarPorId(id);
        if (producto != null) {
            producto.setNombre(nuevoNombre);
            producto.setDescripcion(nuevaDescripcion);
            producto.setPrecio(nuevoPrecio);
            producto.setCantidadStock(nuevaCantidad);
            System.out.println("Producto actualizado exitosamente: " + producto.getNombre());
            return true;
        } else {
            System.err.println("Error: No se encontró un producto con el ID " + id);
            return false;
        }
    }
    
    /**
     * Busca productos por nombre
     * @param nombre Nombre a buscar
     * @return Lista de productos que coinciden con el nombre
     */
    public List<Producto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca productos por descripción
     * @param descripcion Descripción a buscar
     * @return Lista de productos que coinciden con la descripción
     */
    public List<Producto> buscarPorDescripcion(String descripcion) {
        if (descripcion == null || descripcion.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return productos.stream()
                .filter(p -> p.getDescripcion().toLowerCase().contains(descripcion.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca productos por nombre o descripción
     * @param criterio Criterio de búsqueda
     * @return Lista de productos que coinciden con el criterio
     */
    public List<Producto> buscarProductos(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        return productos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(criterio.toLowerCase()) ||
                           p.getDescripcion().toLowerCase().contains(criterio.toLowerCase()))
                .collect(Collectors.toList());
    }
    
    /**
     * Busca un producto por su ID
     * @param id ID del producto a buscar
     * @return Producto encontrado o null si no existe
     */
    public Producto buscarPorId(int id) {
        return productos.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Lista todos los productos del inventario
     * @return Lista de todos los productos
     */
    public List<Producto> listarTodosLosProductos() {
        return new ArrayList<>(productos);
    }
    
    /**
     * Obtiene el número total de productos en el inventario
     * @return Cantidad total de productos
     */
    public int obtenerTotalProductos() {
        return productos.size();
    }
    
    /**
     * Verifica si el inventario está vacío
     * @return true si está vacío, false si tiene productos
     */
    public boolean estaVacio() {
        return productos.isEmpty();
    }
    
    /**
     * Obtiene el siguiente ID disponible
     * @return Siguiente ID a asignar
     */
    public int obtenerSiguienteId() {
        return siguienteId;
    }
    
    /**
     * Muestra información resumida del inventario
     * @return String con información del inventario
     */
    public String obtenerResumenInventario() {
        return String.format("Inventario: %d productos registrados", productos.size());
    }
}

