package com.grupo7.ecommerce.model.decorator;

import com.grupo7.ecommerce.model.Component;

public class DescuentoCuponDecorator extends Decorator {
    private final String cuponValido;
    private final String cuponIngresado;
    private final double porcentaje;

    public DescuentoCuponDecorator(Component component, String cuponValido, String cuponIngresado, double porcentaje) {
        super(component);
        this.cuponValido = cuponValido;
        this.cuponIngresado = cuponIngresado;
        this.porcentaje = porcentaje;
    }

    @Override
    public double getPrecio() {
        double p = component.getPrecio();
        if (cuponIngresado != null && cuponIngresado.equals(cuponValido)) {
            double nuevo = p - (p * porcentaje);
            return nuevo;
        }
        return p;
    }
}

