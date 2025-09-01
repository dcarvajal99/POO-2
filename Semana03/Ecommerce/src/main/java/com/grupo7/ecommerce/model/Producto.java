package com.grupo7.ecommerce.model;

public class Producto implements Component {
    private final String nombre;
    private final String categoria;
    private final double precioBase;

    public Producto(String nombre, String categoria, double precioBase) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioBase = precioBase;
    }

    @Override public String getNombre() { return nombre; }
    @Override public String getCategoria() { return categoria; }
    @Override public double getPrecio() { return precioBase; }
}