package com.grupo7.ecommerce.repository;

import com.grupo7.ecommerce.controller.CarritoController;
import com.grupo7.ecommerce.controller.DescuentoController;
import com.grupo7.ecommerce.controller.ProductoController;
import com.grupo7.ecommerce.model.Component;
import com.grupo7.ecommerce.model.DiscountManager;
import com.grupo7.ecommerce.model.dto.Boleta;
import com.grupo7.ecommerce.model.dto.LineaBoleta;
import com.grupo7.ecommerce.utils.CsvUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataLoader {
    private final ProductoController productoController;
    private final CarritoController carritoController;
    private final DescuentoController descuentoController;

    // Rutas por defecto dentro del proyecto
    private final String basePath = "src/main/java/com/grupo7/ecommerce/data";
    private final String carritoPath = basePath + "/carrito.csv";
    private final String boletasPath = basePath + "/boletas.csv";

    public DataLoader(ProductoController productoController,
                      CarritoController carritoController,
                      DescuentoController descuentoController) {
        this.productoController = productoController;
        this.carritoController = carritoController;
        this.descuentoController = descuentoController;
    }

    public void cargarCatalogoDesdeCSV(String ruta) {
        List<String[]> filas = CsvUtils.readCsv(ruta);
        boolean first = true;
        for (String[] fila : filas) {
            if (first) { first = false; continue; } // cabecera
            if (fila.length < 3) continue;
            String nombre = fila[0];
            String categoria = fila[1];
            double precio = safeDouble(fila[2], 0);
            productoController.agregarProductoCatalogo(nombre, categoria, precio);
        }
    }

    public void cargarCarritoDesdeCSV(String ruta) {
        List<String[]> filas = CsvUtils.readCsv(ruta);
        if (filas.isEmpty()) return;
        // Crear índice por nombre para búsqueda rápida
        Map<String, Component> index = new HashMap<>();
        for (Component c : productoController.listarCatalogo()) {
            index.put(c.getNombre(), c);
        }
        boolean first = true;
        for (String[] fila : filas) {
            if (first) { first = false; continue; }
            if (fila.length == 0) continue;
            String nombre = fila[0];
            Component ref = index.get(nombre);
            if (ref != null) {
                carritoController.agregarProducto(ref);
            }
        }
    }

    public void cargarDescuentosDesdeCSV(String ruta) {
        List<String[]> filas = CsvUtils.readCsv(ruta);
        boolean first = true;
        for (String[] fila : filas) {
            if (first) { first = false; continue; }
            if (fila.length < 2) continue;
            String key = fila[0].toLowerCase();
            String value = fila[1];
            switch (key) {
                case "descuento_global":
                case "porcentaje_global":
                    double p = safeDouble(value, 0);
                    // permitir 0..1 o 0..100
                    if (p > 1) p = p / 100.0;
                    descuentoController.setDescuentoGlobal(p);
                    break;
                case "categoria_promocion":
                case "categoria_desc":
                    descuentoController.setCategoriaPromocion(value == null || value.isBlank() ? null : value);
                    break;
                default:
                    // ignorar otros
            }
        }
    }

    // Guarda el estado actual del carrito en el CSV (sobrescribe)
    public boolean guardarCarritoCSV() {
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"nombre"});
        for (Component c : carritoController.getCarrito().getItems()) {
            rows.add(new String[]{c.getNombre()});
        }
        return CsvUtils.writeCsv(carritoPath, rows, false);
    }

    // Registra una boleta en el archivo de historial (anexa). Formato simple: BOLETA,subtotal,total,cupon y LINEA,... por cada item
    public boolean registrarBoletaCSV(Boleta boleta, String cupon) {
        if (boleta == null) return false;
        List<String[]> rows = new ArrayList<>();
        rows.add(new String[]{"BOLETA", String.valueOf(boleta.getLineas().size()), String.valueOf(boleta.getSubtotal()), String.valueOf(boleta.getTotal()), cupon == null ? "" : cupon});
        for (LineaBoleta l : boleta.getLineas()) {
            rows.add(new String[]{"LINEA", l.getNombre(), l.getCategoria(), String.valueOf(l.getPrecioBase()), String.valueOf(l.getPrecioFinal())});
        }
        // añadir línea vacía como separador
        rows.add(new String[]{""});
        return CsvUtils.writeCsv(boletasPath, rows, true);
    }

    // Carga todas las boletas registradas en boletas.csv
    public List<Boleta> cargarBoletasCSV() {
        List<Boleta> out = new ArrayList<>();
        List<String[]> filas = CsvUtils.readCsv(boletasPath);
        Boleta current = null;
        for (String[] fila : filas) {
            if (fila.length == 0 || fila[0] == null || fila[0].isBlank()) { current = null; continue; }
            String tipo = fila[0];
            if ("BOLETA".equalsIgnoreCase(tipo)) {
                current = new Boleta();
                out.add(current);
            } else if ("LINEA".equalsIgnoreCase(tipo) && current != null) {
                String nombre = fila.length > 1 ? fila[1] : "";
                String categoria = fila.length > 2 ? fila[2] : "";
                double precioBase = fila.length > 3 ? safeDouble(fila[3], 0) : 0;
                double precioFinal = fila.length > 4 ? safeDouble(fila[4], precioBase) : precioBase;
                current.agregarLinea(new LineaBoleta(nombre, categoria, precioBase, precioFinal));
            } else {
                // ignorar otras líneas
            }
        }
        return out;
    }

    private static double safeDouble(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }
}
