package com.grupo7.gestioninventariojava.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para la clase Producto
 * Verifica la creación, actualización y comportamiento de los productos
 * 
 *@author grupo7
 */
public class ProductoTest {
    
    private Producto producto;
    
    @BeforeEach
    public void setUp() {
        producto = new Producto();
    }
    
    /**
     * Prueba la creación de un producto con constructor por defecto
     */
    @Test
    public void testCreacionProductoConstructorDefecto() {
        assertNotNull(producto);
        assertEquals(0, producto.getId());
        assertEquals("", producto.getNombre());
        assertEquals("", producto.getDescripcion());
        assertEquals(0.0, producto.getPrecio(), 0.001);
        assertEquals(0, producto.getCantidadStock());
    }
    
    /**
     * Prueba la creación de un producto con constructor con parámetros
     */
    @Test
    public void testCreacionProductoConParametros() {
        Producto productoCompleto = new Producto(1, "Laptop", "Laptop gaming", 1500.0, 10);
        
        assertNotNull(productoCompleto);
        assertEquals(1, productoCompleto.getId());
        assertEquals("Laptop", productoCompleto.getNombre());
        assertEquals("Laptop gaming", productoCompleto.getDescripcion());
        assertEquals(1500.0, productoCompleto.getPrecio(), 0.001);
        assertEquals(10, productoCompleto.getCantidadStock());
    }
    
    /**
     * Prueba la actualización del ID del producto
     */
    @Test
    public void testActualizacionId() {
        producto.setId(5);
        assertEquals(5, producto.getId());
    }
    
    /**
     * Prueba la actualización del nombre del producto
     */
    @Test
    public void testActualizacionNombre() {
        producto.setNombre("Mouse");
        assertEquals("Mouse", producto.getNombre());
    }
    
    /**
     * Prueba la actualización de la descripción del producto
     */
    @Test
    public void testActualizacionDescripcion() {
        producto.setDescripcion("Mouse inalámbrico");
        assertEquals("Mouse inalámbrico", producto.getDescripcion());
    }
    
    /**
     * Prueba la actualización del precio del producto
     */
    @Test
    public void testActualizacionPrecio() {
        producto.setPrecio(25.99);
        assertEquals(25.99, producto.getPrecio(), 0.001);
    }
    
    /**
     * Prueba la actualización de la cantidad en stock
     */
    @Test
    public void testActualizacionCantidadStock() {
        producto.setCantidadStock(50);
        assertEquals(50, producto.getCantidadStock());
    }
    
    /**
     * Prueba el método toString del producto
     */
    @Test
    public void testToString() {
        producto.setId(1);
        producto.setNombre("Teclado");
        producto.setDescripcion("Teclado mecánico");
        producto.setPrecio(89.99);
        producto.setCantidadStock(15);
        
        String resultado = producto.toString();
        assertTrue(resultado.contains("ID: 1"));
        assertTrue(resultado.contains("Nombre: Teclado"));
        assertTrue(resultado.contains("Descripción: Teclado mecánico"));
        assertTrue(resultado.contains("Precio: $89,99"));
        assertTrue(resultado.contains("Stock: 15"));
    }
    
    /**
     * Prueba el método equals con productos iguales
     */
    @Test
    public void testEqualsProductosIguales() {
        Producto producto1 = new Producto(1, "Monitor", "Monitor 24 pulgadas", 200.0, 5);
        Producto producto2 = new Producto(1, "Monitor", "Monitor 24 pulgadas", 200.0, 5);
        
        assertTrue(producto1.equals(producto2));
    }
    
    /**
     * Prueba el método equals con productos diferentes
     */
    @Test
    public void testEqualsProductosDiferentes() {
        Producto producto1 = new Producto(1, "Monitor", "Monitor 24 pulgadas", 200.0, 5);
        Producto producto2 = new Producto(2, "Monitor", "Monitor 24 pulgadas", 200.0, 5);
        
        assertFalse(producto1.equals(producto2));
    }
    
    /**
     * Prueba el método equals con objeto nulo
     */
    @Test
    public void testEqualsConNulo() {
        Producto producto1 = new Producto(1, "Monitor", "Monitor 24 pulgadas", 200.0, 5);
        
        assertFalse(producto1.equals(null));
    }
    
    /**
     * Prueba el método equals con objeto de diferente clase
     */
    @Test
    public void testEqualsConDiferenteClase() {
        Producto producto1 = new Producto(1, "Monitor", "Monitor 24 pulgadas", 200.0, 5);
        String objetoDiferente = "No es un producto";
        
        assertFalse(producto1.equals(objetoDiferente));
    }
    
    /**
     * Prueba el método hashCode
     */
    @Test
    public void testHashCode() {
        Producto producto1 = new Producto(1, "Monitor", "Monitor 24 pulgadas", 200.0, 5);
        Producto producto2 = new Producto(1, "Monitor", "Monitor 24 pulgadas", 200.0, 5);
        
        assertEquals(producto1.hashCode(), producto2.hashCode());
    }
    
    /**
     * Prueba la consistencia entre equals y hashCode
     */
    @Test
    public void testConsistenciaEqualsHashCode() {
        Producto producto1 = new Producto(1, "Monitor", "Monitor 24 pulgadas", 200.0, 5);
        Producto producto2 = new Producto(1, "Monitor", "Monitor 24 pulgadas", 200.0, 5);
        
        if (producto1.equals(producto2)) {
            assertEquals(producto1.hashCode(), producto2.hashCode());
        }
    }
}
