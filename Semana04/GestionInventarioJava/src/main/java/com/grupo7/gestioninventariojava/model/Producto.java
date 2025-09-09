package com.grupo7.gestioninventariojava.model;

/**
 * Clase Producto que representa los items individuales del inventario
 * Incluye atributos básicos como ID, nombre, descripción, precio y cantidad en stock
 * 
 *@author grupo7
 */
public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int cantidadStock;
    
    /**
     * Constructor por defecto
     */
    public Producto() {
        this.id = 0;
        this.nombre = "";
        this.descripcion = "";
        this.precio = 0.0;
        this.cantidadStock = 0;
    }
    
    /**
     * Constructor con parámetros
     * @param id Identificador único del producto
     * @param nombre Nombre del producto
     * @param descripcion Descripción del producto
     * @param precio Precio del producto
     * @param cantidadStock Cantidad disponible en stock
     */
    public Producto(int id, String nombre, String descripcion, double precio, int cantidadStock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidadStock = cantidadStock;
    }
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public double getPrecio() {
        return precio;
    }
    
    public int getCantidadStock() {
        return cantidadStock;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public void setCantidadStock(int cantidadStock) {
        this.cantidadStock = cantidadStock;
    }
    
    /**
     * Método toString para mostrar información del producto
     * @return String con la información formateada del producto
     */
    @Override
    public String toString() {
        return String.format("ID: %d | Nombre: %s | Descripción: %s | Precio: $%.2f | Stock: %d",
                id, nombre, descripcion, precio, cantidadStock);
    }
    
    /**
     * Método equals para comparar productos por ID
     * @param obj Objeto a comparar
     * @return true si los productos tienen el mismo ID
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Producto producto = (Producto) obj;
        return id == producto.id;
    }
    
    /**
     * Método hashCode para mantener consistencia con equals
     * @return hashCode basado en el ID
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

