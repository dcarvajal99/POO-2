package com.grupo7.ecommerce.commands;

import com.grupo7.ecommerce.model.DiscountManager;

public class SetCuponValidoCommand implements Command {
    private final String cupon;
    public SetCuponValidoCommand(String cupon) {
        this.cupon = cupon;
    }
    @Override
    public void Ejecutar() {
        DiscountManager.getInstance().setCuponValido(cupon);
        System.out.println("Cupón válido actualizado: " + cupon);
    }
}


