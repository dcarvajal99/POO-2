package com.grupo7.ecommerce.commands;

import com.grupo7.ecommerce.model.DiscountManager;

public class SetDescuentoGlobalCommand implements Command {
    private final double porcentaje; // 0..1
    public SetDescuentoGlobalCommand(double porcentaje) {
        this.porcentaje = porcentaje;
    }
    @Override
    public void Ejecutar() {
        DiscountManager.getInstance().setPorcentajeGlobal(porcentaje);
        System.out.println("Descuento global actualizado a: " + (int)(porcentaje * 100) + "%");
    }
}

