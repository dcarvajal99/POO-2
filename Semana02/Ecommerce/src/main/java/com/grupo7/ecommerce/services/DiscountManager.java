package com.grupo7.ecommerce.services;

import com.grupo7.ecommerce.model.Component;
import com.grupo7.ecommerce.model.Producto;
import com.grupo7.ecommerce.model.decorator.DescuentoCategoriaDecorator;
import com.grupo7.ecommerce.model.decorator.DescuentoGlobalDecorator;
import com.grupo7.ecommerce.model.decorator.DescuentoCuponDecorator;

public class DiscountManager {
    private static DiscountManager instance;

    private double porcentajeGlobal = 0.10;
    private String  cuponValido      = "DESCUENTO50";
    private String  categoriaDesc    = "Ropa";

    private DiscountManager() {}

    public static synchronized DiscountManager getInstance() {
        if (instance == null) instance = new DiscountManager();
        return instance;
    }

    public double aplicarDescuento(String nombreProducto, double precioOriginal, String categoriaProducto, String cuponProducto) {
        Component producto = new Producto(nombreProducto, categoriaProducto, precioOriginal);

        // Encadenar decoradores según reglas
        Component decorado = producto;

        if (porcentajeGlobal > 0) {
            decorado = new DescuentoGlobalDecorator(decorado, porcentajeGlobal);
        }
        if (cuponProducto != null && cuponProducto.equals(cuponValido)) {
            decorado = new DescuentoCuponDecorator(decorado, cuponValido, cuponProducto, 0.50);
        }
        if (categoriaProducto != null && categoriaProducto.equalsIgnoreCase(categoriaDesc)) {
            decorado = new DescuentoCategoriaDecorator(decorado, categoriaDesc);
        }

        return decorado.getPrecio();
    }
}