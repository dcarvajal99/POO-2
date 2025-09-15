package com.grupo7.gestioninventariojava.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para la clase Inventario
 * Verifica las operaciones de agregar, eliminar, buscar y listar productos
 * 
 *@author grupo7
 */
public class InventarioTest {
    
    private Inventario inventario;
    private Producto producto1;
    private Producto producto2;
    private Producto producto3;
    
    @BeforeEach
    public void setUp() {
        inventario = new Inventario();
        producto1 = new Producto(1, "Laptop", "Laptop gaming", 1500.0, 5);
        producto2 = new Producto(2, "Mouse", "Mouse inalámbrico", 25.99, 20);
        producto3 = new Producto(3, "Teclado", "Teclado mecánico", 89.99, 15);
    }
    
    /**
     * Prueba la creación de un inventario vacío
     */
    @Test
    public void testCreacionInventarioVacio() {
        assertNotNull(inventario);
        assertTrue(inventario.estaVacio());
        assertEquals(0, inventario.obtenerTotalProductos());
    }
    
    /**
     * Prueba agregar un producto al inventario
     */
    @Test
    public void testAgregarProducto() {
        boolean resultado = inventario.agregarProducto(producto1);
        
        assertTrue(resultado);
        assertFalse(inventario.estaVacio());
        assertEquals(1, inventario.obtenerTotalProductos());
        assertTrue(inventario.listarTodosLosProductos().contains(producto1));
    }
    
    /**
     * Prueba agregar un producto nulo
     */
    @Test
    public void testAgregarProductoNulo() {
        boolean resultado = inventario.agregarProducto(null);
        
        assertFalse(resultado);
        assertTrue(inventario.estaVacio());
        assertEquals(0, inventario.obtenerTotalProductos());
    }
    
    /**
     * Prueba agregar múltiples productos
     */
    @Test
    public void testAgregarMultiplesProductos() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        inventario.agregarProducto(producto3);
        
        assertEquals(3, inventario.obtenerTotalProductos());
        assertFalse(inventario.estaVacio());
    }
    
    /**
     * Prueba eliminar un producto existente por ID
     */
    @Test
    public void testEliminarProductoExistente() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        
        boolean resultado = inventario.eliminarProducto(1);
        
        assertTrue(resultado);
        assertEquals(1, inventario.obtenerTotalProductos());
        assertNull(inventario.buscarPorId(1));
        assertNotNull(inventario.buscarPorId(2));
    }
    
    /**
     * Prueba eliminar un producto con ID inexistente
     */
    @Test
    public void testEliminarProductoInexistente() {
        inventario.agregarProducto(producto1);
        
        boolean resultado = inventario.eliminarProducto(999);
        
        assertFalse(resultado);
        assertEquals(1, inventario.obtenerTotalProductos());
        assertNotNull(inventario.buscarPorId(1));
    }
    
    /**
     * Prueba eliminar producto de inventario vacío
     */
    @Test
    public void testEliminarProductoInventarioVacio() {
        boolean resultado = inventario.eliminarProducto(1);
        
        assertFalse(resultado);
        assertTrue(inventario.estaVacio());
    }
    
    /**
     * Prueba actualizar un producto existente
     */
    @Test
    public void testActualizarProductoExistente() {
        inventario.agregarProducto(producto1);
        
        boolean resultado = inventario.actualizarProducto(1, "Laptop Pro", "Laptop profesional", 2000.0, 3);
        
        assertTrue(resultado);
        Producto productoActualizado = inventario.buscarPorId(1);
        assertEquals("Laptop Pro", productoActualizado.getNombre());
        assertEquals("Laptop profesional", productoActualizado.getDescripcion());
        assertEquals(2000.0, productoActualizado.getPrecio(), 0.001);
        assertEquals(3, productoActualizado.getCantidadStock());
    }
    
    /**
     * Prueba actualizar un producto inexistente
     */
    @Test
    public void testActualizarProductoInexistente() {
        inventario.agregarProducto(producto1);
        
        boolean resultado = inventario.actualizarProducto(999, "Nuevo", "Nueva descripción", 100.0, 1);
        
        assertFalse(resultado);
        // Verificar que el producto original no cambió
        Producto productoOriginal = inventario.buscarPorId(1);
        assertEquals("Laptop", productoOriginal.getNombre());
    }
    
    /**
     * Prueba buscar producto por ID existente
     */
    @Test
    public void testBuscarPorIdExistente() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        
        Producto resultado = inventario.buscarPorId(2);
        
        assertNotNull(resultado);
        assertEquals(producto2, resultado);
    }
    
    /**
     * Prueba buscar producto por ID inexistente
     */
    @Test
    public void testBuscarPorIdInexistente() {
        inventario.agregarProducto(producto1);
        
        Producto resultado = inventario.buscarPorId(999);
        
        assertNull(resultado);
    }
    
    /**
     * Prueba buscar productos por nombre existente
     */
    @Test
    public void testBuscarPorNombreExistente() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        inventario.agregarProducto(producto3);
        
        List<Producto> resultados = inventario.buscarPorNombre("Mouse");
        
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertTrue(resultados.contains(producto2));
    }
    
    /**
     * Prueba buscar productos por nombre parcial
     */
    @Test
    public void testBuscarPorNombreParcial() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        inventario.agregarProducto(producto3);
        
        List<Producto> resultados = inventario.buscarPorNombre("Lap");
        
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertTrue(resultados.contains(producto1));
    }
    
    /**
     * Prueba buscar productos por nombre inexistente
     */
    @Test
    public void testBuscarPorNombreInexistente() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        
        List<Producto> resultados = inventario.buscarPorNombre("Tablet");
        
        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
    }
    
    /**
     * Prueba buscar productos por descripción existente
     */
    @Test
    public void testBuscarPorDescripcionExistente() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        inventario.agregarProducto(producto3);
        
        List<Producto> resultados = inventario.buscarPorDescripcion("gaming");
        
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertTrue(resultados.contains(producto1));
    }
    
    /**
     * Prueba buscar productos por criterio general (nombre o descripción)
     */
    @Test
    public void testBuscarProductosPorCriterio() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        inventario.agregarProducto(producto3);
        
        List<Producto> resultados = inventario.buscarProductos("Mouse");
        
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertTrue(resultados.contains(producto2));
    }
    
    /**
     * Prueba buscar productos por criterio que coincide en descripción
     */
    @Test
    public void testBuscarProductosPorCriterioDescripcion() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        inventario.agregarProducto(producto3);
        
        List<Producto> resultados = inventario.buscarProductos("gaming");
        
        assertNotNull(resultados);
        assertEquals(1, resultados.size());
        assertTrue(resultados.contains(producto1));
    }
    
    /**
     * Prueba buscar productos con criterio vacío
     */
    @Test
    public void testBuscarProductosCriterioVacio() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        
        List<Producto> resultados = inventario.buscarProductos("");
        
        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
    }
    
    /**
     * Prueba buscar productos con criterio nulo
     */
    @Test
    public void testBuscarProductosCriterioNulo() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        
        List<Producto> resultados = inventario.buscarProductos(null);
        
        assertNotNull(resultados);
        assertTrue(resultados.isEmpty());
    }
    
    /**
     * Prueba listar todos los productos
     */
    @Test
    public void testListarTodosLosProductos() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        inventario.agregarProducto(producto3);
        
        List<Producto> todosLosProductos = inventario.listarTodosLosProductos();
        
        assertNotNull(todosLosProductos);
        assertEquals(3, todosLosProductos.size());
        assertTrue(todosLosProductos.contains(producto1));
        assertTrue(todosLosProductos.contains(producto2));
        assertTrue(todosLosProductos.contains(producto3));
    }
    
    /**
     * Prueba listar productos de inventario vacío
     */
    @Test
    public void testListarProductosInventarioVacio() {
        List<Producto> todosLosProductos = inventario.listarTodosLosProductos();
        
        assertNotNull(todosLosProductos);
        assertTrue(todosLosProductos.isEmpty());
    }
    
    /**
     * Prueba obtener resumen del inventario
     */
    @Test
    public void testObtenerResumenInventario() {
        inventario.agregarProducto(producto1);
        inventario.agregarProducto(producto2);
        
        String resumen = inventario.obtenerResumenInventario();
        
        assertNotNull(resumen);
        assertTrue(resumen.contains("2 productos registrados"));
    }
    
    /**
     * Prueba obtener siguiente ID
     */
    @Test
    public void testObtenerSiguienteId() {
        assertEquals(1, inventario.obtenerSiguienteId());
        
        // Crear productos sin ID para que se asignen automáticamente
        Producto productoSinId1 = new Producto(0, "Producto 1", "Descripción 1", 100.0, 5);
        Producto productoSinId2 = new Producto(0, "Producto 2", "Descripción 2", 200.0, 10);
        
        inventario.agregarProducto(productoSinId1);
        assertEquals(2, inventario.obtenerSiguienteId());
        
        inventario.agregarProducto(productoSinId2);
        assertEquals(3, inventario.obtenerSiguienteId());
    }
}
