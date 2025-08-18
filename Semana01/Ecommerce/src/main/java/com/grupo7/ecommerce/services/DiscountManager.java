package com.grupo7.ecommerce.services;

public class DiscountManager {
    private static DiscountManager instance;
    private double porcentajeGlobal = 0.1; // Descuento global del 10%

    private String cuponValido = "DESCUENTO50"; // Ejemplo de cupón válido
    private double porcentajeCupon = 0.5; // Descuento del cupón, por defecto 0%

    private String categoriaDescuento = "Ropa"; // Categoría con descuento
    private double porcentajeDescuentoCategoria = 0.2; // Descuento del 20% para la categoría

    private DiscountManager() {
    }

    public static DiscountManager getInstance() {
        if (instance == null) {
            instance = new DiscountManager();
        }
        return instance;
    }

    public double aplicarDescuento(double precioOriginal, String categoriaProducto, String cuponProducto) {
        double precioConDescuento = precioOriginal;

        // Aplicar descuento global
        precioConDescuento -= precioConDescuento * porcentajeGlobal;

        // Aplicar descuento por cupón si es válido
        if (cuponProducto != null && cuponProducto.equals(cuponValido)) {
            precioConDescuento -= precioConDescuento * porcentajeCupon;
        }

        // Aplicar descuento por categoría si corresponde
        if (categoriaProducto != null && categoriaProducto.equals(categoriaDescuento)) {
            precioConDescuento -= precioConDescuento * porcentajeDescuentoCategoria;
        }

        return precioConDescuento;
    }
}
