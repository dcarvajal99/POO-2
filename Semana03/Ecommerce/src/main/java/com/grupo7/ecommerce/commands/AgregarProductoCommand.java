package com.grupo7.ecommerce.commands;

import com.grupo7.ecommerce.model.Component;
import com.grupo7.ecommerce.model.Carrito;

public class AgregarProductoCommand implements Command {
    private final Carrito carrito;
    private final Component producto;

    public AgregarProductoCommand(Carrito carrito, Component producto) {
        this.carrito = carrito;
        this.producto = producto;
    }

    @Override
    public void Ejecutar() {
        carrito.agregar(producto);
        System.out.println("Producto agregado al carrito: " + producto.getNombre());
    }
}