package com.grupo7.ecommerce.model;

/**
 * Componente base del patrón Decorator.
 */
public interface Component {
    String getNombre();
    String getCategoria();
    double getPrecio();
}