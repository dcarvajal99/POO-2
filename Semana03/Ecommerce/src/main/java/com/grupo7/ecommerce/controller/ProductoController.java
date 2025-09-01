package com.grupo7.ecommerce.controller;

import com.grupo7.ecommerce.model.Component;
import com.grupo7.ecommerce.model.Producto;

import java.util.ArrayList;
import java.util.List;

public class ProductoController {
    private final List<Component> catalogo = new ArrayList<>();

    public void agregarProductoCatalogo(String nombre, String categoria, double precio) {
        catalogo.add(new Producto(nombre, categoria, precio));
    }

    public List<Component> listarCatalogo() {
        return new ArrayList<>(catalogo);
    }
}

