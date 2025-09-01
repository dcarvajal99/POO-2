package com.grupo7.ecommerce;

import com.grupo7.ecommerce.controller.CarritoController;
import com.grupo7.ecommerce.controller.DescuentoController;
import com.grupo7.ecommerce.controller.ProductoController;
import com.grupo7.ecommerce.repository.DataLoader;
import com.grupo7.ecommerce.model.Carrito;
import com.grupo7.ecommerce.ui.ConsoleUI;
import com.grupo7.ecommerce.view.CarritoView;
import com.grupo7.ecommerce.view.ProductoView;
import com.grupo7.ecommerce.view.DescuentoView;

public class App {
    public static void main(String[] args) {
        // Controladores
        ProductoController productoController = new ProductoController();
        CarritoController carritoController = new CarritoController(new Carrito());
        DescuentoController descuentoController = new DescuentoController();

        // Cargar datos desde CSV (carpeta data)
        DataLoader loader = new DataLoader(productoController, carritoController, descuentoController);
        String base = "src/main/java/com/grupo7/ecommerce/data";
        loader.cargarCatalogoDesdeCSV(base + "/productos.csv");
        loader.cargarDescuentosDesdeCSV(base + "/descuentos.csv");
        loader.cargarCarritoDesdeCSV(base + "/carrito.csv");

        // Vistas
        ProductoView productoView = new ProductoView();
        CarritoView carritoView = new CarritoView();
        DescuentoView descuentoView = new DescuentoView();

        // UI
        ConsoleUI ui = new ConsoleUI(
                productoController,
                carritoController,
                descuentoController,
                productoView,
                carritoView,
                descuentoView,
                loader
        );
        ui.run();
    }
}
