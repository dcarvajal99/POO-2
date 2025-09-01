package com.grupo7.ecommerce.model;

import com.grupo7.ecommerce.model.decorator.DescuentoCategoriaDecorator;
import com.grupo7.ecommerce.model.decorator.DescuentoCuponDecorator;
import com.grupo7.ecommerce.model.decorator.DescuentoGlobalDecorator;

/**
 * Singleton responsable de aplicar descuentos utilizando el patrón Decorator.
 */
public class DiscountManager {
    private static DiscountManager instance;

    // Configuración por defecto
    private double porcentajeGlobal = 0.10; // 10%
    private String cuponValido = "DESCUENTO50";
    private String categoriaDesc = "Ropa";

    private DiscountManager() {}

    public static synchronized DiscountManager getInstance() {
        if (instance == null) instance = new DiscountManager();
        return instance;
    }

    // Setters con normalización/validación
    public void setPorcentajeGlobal(double porcentajeGlobal) {
        if (Double.isNaN(porcentajeGlobal) || Double.isInfinite(porcentajeGlobal)) porcentajeGlobal = 0;
        this.porcentajeGlobal = Math.max(0, Math.min(1, porcentajeGlobal));
    }
    public void setCuponValido(String cuponValido) {
        this.cuponValido = cuponValido == null ? null : cuponValido.trim();
    }
    public void setCategoriaDesc(String categoriaDesc) {
        this.categoriaDesc = categoriaDesc == null ? null : categoriaDesc.trim();
    }

    public double getPorcentajeGlobal() { return porcentajeGlobal; }
    public String getCuponValido() { return cuponValido; }
    public String getCategoriaDesc() { return categoriaDesc; }

    /**
     * Aplica los descuentos configurados al producto dado, encadenando decoradores.
     * @param nombreProducto nombre
     * @param precioOriginal precio base
     * @param categoriaProducto categoria
     * @param cuponIngresado cupón ingresado (puede ser null)
     * @return precio final con descuentos
     */
    public double aplicarDescuento(String nombreProducto, double precioOriginal, String categoriaProducto, String cuponIngresado) {
        if (Double.isNaN(precioOriginal) || Double.isInfinite(precioOriginal) || precioOriginal < 0) precioOriginal = 0;
        Component producto = new Producto(nombreProducto, categoriaProducto, precioOriginal);

        Component decorado = producto;
        if (porcentajeGlobal > 0) {
            decorado = new DescuentoGlobalDecorator(decorado, porcentajeGlobal);
        }
        String cuponIn = cuponIngresado == null ? null : cuponIngresado.trim();
        if (cuponIn != null && cuponValido != null && cuponIn.equalsIgnoreCase(cuponValido)) {
            decorado = new DescuentoCuponDecorator(decorado, cuponValido, cuponIn, 0.50);
        }
        if (categoriaProducto != null && categoriaDesc != null && categoriaProducto.equalsIgnoreCase(categoriaDesc)) {
            decorado = new DescuentoCategoriaDecorator(decorado, categoriaDesc);
        }
        double result = decorado.getPrecio();
        if (Double.isNaN(result) || Double.isInfinite(result)) result = precioOriginal;
        // Garantizar límites [0, precioOriginal]
        result = Math.max(0, Math.min(precioOriginal, result));
        return result;
    }
}
