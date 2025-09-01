package com.grupo7.ecommerce.model.decorator;

import com.grupo7.ecommerce.model.Component;

public class DescuentoGlobalDecorator extends Decorator {
    private final double porcentaje;
    public DescuentoGlobalDecorator(Component component, double porcentaje) {
        super(component);
        this.porcentaje = porcentaje;
    }
    @Override
    public double getPrecio() {
        double p = component.getPrecio();
        double nuevo = p - (p * porcentaje);
        return nuevo;
    }
}