package com.grupo7.ecommerce.commands;

import com.grupo7.ecommerce.model.Producto;
import com.grupo7.ecommerce.model.Carrito;

public class AgregarProductoCommand implements Command {
    private final Carrito carrito;
    private final Producto producto;

    public AgregarProductoCommand(Carrito carrito, Producto producto) {
        this.carrito = carrito;
        this.producto = producto;
    }

    @Override
    public void ejecutar() {
        carrito.agregar(producto);
        System.out.println("Producto agregado al carrito: " + producto.getNombre());
    }
}