package com.grupo7.ecommerce.controller;

import com.grupo7.ecommerce.commands.AgregarProductoCommand;
import com.grupo7.ecommerce.commands.EliminarProductoCommand;
import com.grupo7.ecommerce.commands.Invoker;
import com.grupo7.ecommerce.model.Carrito;
import com.grupo7.ecommerce.model.Component;
import com.grupo7.ecommerce.model.DiscountManager;
import com.grupo7.ecommerce.model.dto.Boleta;
import com.grupo7.ecommerce.model.dto.LineaBoleta;

public class CarritoController {
    private final Carrito carrito;
    private final Invoker invoker = new Invoker();

    public CarritoController(Carrito carrito) {
        this.carrito = carrito;
    }

    public void agregarProducto(Component p) {
        invoker.agregarComando(new AgregarProductoCommand(carrito, p));
        invoker.ejecutarComandos();
    }

    public void eliminarProducto(Component p) {
        invoker.agregarComando(new EliminarProductoCommand(carrito, p));
        invoker.ejecutarComandos();
    }

    public Carrito getCarrito() { return carrito; }

    // Nueva: generar DTO Boleta separando lógica de descuentos de la vista
    public Boleta generarBoleta(String cupon) {
        DiscountManager dm = DiscountManager.getInstance();
        Boleta boleta = new Boleta();
        for (Component c : carrito.getItems()) {
            double base = c.getPrecio();
            double finalP = dm.aplicarDescuento(c.getNombre(), base, c.getCategoria(), cupon);
            boleta.agregarLinea(new LineaBoleta(c.getNombre(), c.getCategoria(), base, finalP));
        }
        return boleta;
    }
}
