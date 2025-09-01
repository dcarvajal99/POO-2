package com.grupo7.ecommerce.model.dto;

public class LineaBoleta {
    private final String nombre;
    private final String categoria;
    private final double precioBase;
    private final double precioFinal;

    public LineaBoleta(String nombre, String categoria, double precioBase, double precioFinal) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.precioBase = precioBase;
        this.precioFinal = precioFinal;
    }

    public String getNombre() { return nombre; }
    public String getCategoria() { return categoria; }
    public double getPrecioBase() { return precioBase; }
    public double getPrecioFinal() { return precioFinal; }
    public double getAhorro() { return Math.max(0, precioBase - precioFinal); }
}

