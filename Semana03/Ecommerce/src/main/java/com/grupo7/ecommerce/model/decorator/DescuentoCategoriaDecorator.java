package com.grupo7.ecommerce.model.decorator;

import com.grupo7.ecommerce.model.Component;

public class DescuentoCategoriaDecorator extends Decorator {
    private final String categoria;
    public DescuentoCategoriaDecorator(Component component, String categoria) {
        super(component);
        this.categoria = categoria;
    }
    @Override
    public double getPrecio() {
        double p = component.getPrecio();
        if (component.getCategoria() != null && component.getCategoria().equalsIgnoreCase(categoria)) {
            double nuevo = p * 0.80; // 20% de descuento
            return nuevo;
        }
        return p;
    }
}