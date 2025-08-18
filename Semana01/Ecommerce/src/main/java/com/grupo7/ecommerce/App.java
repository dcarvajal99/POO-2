/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.grupo7.ecommerce;

import com.grupo7.ecommerce.services.DiscountManager;

/**
 *
 * @author grupo7
 */
public class App {

    public static void main(String[] args) {

        // Obtener la instancia de DiscountManager
        DiscountManager gestorDescuentos = DiscountManager.getInstance();

        // Simulación de un producto
        String nombreProducto = "Poleron";
        double precioOriginal = 30000;
        String categoriaProducto = "Ropa"; // Categoría del producto
        String cuponProducto = "DESCUENTO50"; // Cupón aplicado

        // Aplicar descuento
        double precioConDescuento = gestorDescuentos.aplicarDescuento(precioOriginal, categoriaProducto, cuponProducto);

        // Mostrar resultado
        System.out.println(":::: Bienvenidos a Tienda Ropa On Line::::");
        System.out.println("Producto: " + nombreProducto);
        System.out.println("Precio original: $" + precioOriginal);
        System.out.println("Precio con descuento: $" + precioConDescuento);



    }
}
