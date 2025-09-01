package com.grupo7.ecommerce.commands;

import com.grupo7.ecommerce.model.Carrito;
import com.grupo7.ecommerce.model.Producto;

public class EliminarProductoCommand implements Command {
    private final Carrito carrito;
    private final Producto producto;

    public EliminarProductoCommand(Carrito carrito, Producto producto) {
        this.carrito = carrito;
        this.producto = producto;
    }

    @Override
    public void ejecutar() {
        boolean ok = carrito.eliminar(producto);
        if (ok) {
            System.out.println("Producto eliminado del carrito: " + producto.getNombre());
        } else {
            System.out.println("Producto no encontrado en el carrito: " + producto.getNombre());
        }
    }
}