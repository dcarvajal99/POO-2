package com.grupo7.gestioninventariojava.controller;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.grupo7.gestioninventariojava.model.Inventario;
import com.grupo7.gestioninventariojava.model.Producto;

/**
 * Pruebas unitarias para la clase InventarioController
 * Verifica la lógica de negocio y la comunicación entre vista y modelo
 * 
 *@author grupo7
 */
public class InventarioControllerTest {
    
    private InventarioController controller;
    private Inventario inventario;
    
    @BeforeEach
    public void setUp() {
        inventario = new Inventario();
        controller = new InventarioController(inventario);
    }
    
    /**
     * Prueba la creación del controlador
     */
    @Test
    public void testCreacionController() {
        assertNotNull(controller);
        assertTrue(controller.estaInventarioVacio());
    }
    
    /**
     * Prueba agregar producto exitoso
     */
    @Test
    public void testAgregarProductoExitoso() {
        InventarioController.ResultadoOperacion resultado = controller.agregarProducto("Laptop", "Laptop gaming", 1500.0, 5);
        
        assertTrue(resultado.isExito());
        assertTrue(resultado.getMensaje().contains("Producto agregado exitosamente"));
        assertEquals(1, controller.obtenerTotalProductos());
    }
    
    /**
     * Prueba agregar producto con nombre vacío
     */
    @Test
    public void testAgregarProductoNombreVacio() {
        InventarioController.ResultadoOperacion resultado = controller.agregarProducto("", "Descripción", 100.0, 5);
        
        assertFalse(resultado.isExito());
        assertTrue(resultado.getMensaje().contains("El nombre del producto no puede estar vacío"));
    }
    
    /**
     * Prueba agregar producto con precio negativo
     */
    @Test
    public void testAgregarProductoPrecioNegativo() {
        InventarioController.ResultadoOperacion resultado = controller.agregarProducto("Producto", "Descripción", -100.0, 5);
        
        assertFalse(resultado.isExito());
        assertTrue(resultado.getMensaje().contains("El precio no puede ser negativo"));
    }
    
    /**
     * Prueba agregar producto con cantidad negativa
     */
    @Test
    public void testAgregarProductoCantidadNegativa() {
        InventarioController.ResultadoOperacion resultado = controller.agregarProducto("Producto", "Descripción", 100.0, -5);
        
        assertFalse(resultado.isExito());
        assertTrue(resultado.getMensaje().contains("La cantidad en stock no puede ser negativa"));
    }
    
    /**
     * Prueba eliminar producto exitoso
     */
    @Test
    public void testEliminarProductoExitoso() {
        controller.agregarProducto("Laptop", "Laptop gaming", 1500.0, 5);
        
        InventarioController.ResultadoOperacion resultado = controller.eliminarProducto(1);
        
        assertTrue(resultado.isExito());
        assertTrue(resultado.getMensaje().contains("Producto eliminado exitosamente"));
        assertEquals(0, controller.obtenerTotalProductos());
    }
    
    /**
     * Prueba eliminar producto con ID inválido
     */
    @Test
    public void testEliminarProductoIdInvalido() {
        InventarioController.ResultadoOperacion resultado = controller.eliminarProducto(0);
        
        assertFalse(resultado.isExito());
        assertTrue(resultado.getMensaje().contains("ID debe ser un número positivo"));
    }
    
    /**
     * Prueba eliminar producto inexistente
     */
    @Test
    public void testEliminarProductoInexistente() {
        InventarioController.ResultadoOperacion resultado = controller.eliminarProducto(999);
        
        assertFalse(resultado.isExito());
        assertTrue(resultado.getMensaje().contains("No se encontró un producto con el ID"));
    }
    
    /**
     * Prueba actualizar producto exitoso
     */
    @Test
    public void testActualizarProductoExitoso() {
        controller.agregarProducto("Laptop", "Laptop gaming", 1500.0, 5);
        
        InventarioController.ResultadoOperacion resultado = controller.actualizarProducto(1, "Laptop Pro", "Laptop profesional", 2000.0, 3);
        
        assertTrue(resultado.isExito());
        assertTrue(resultado.getMensaje().contains("Producto actualizado exitosamente"));
        
        Producto productoActualizado = controller.buscarProductoPorId(1);
        assertEquals("Laptop Pro", productoActualizado.getNombre());
        assertEquals("Laptop profesional", productoActualizado.getDescripcion());
        assertEquals(2000.0, productoActualizado.getPrecio(), 0.001);
        assertEquals(3, productoActualizado.getCantidadStock());
    }
    
    /**
     * Prueba actualizar producto con datos inválidos
     */
    @Test
    public void testActualizarProductoDatosInvalidos() {
        controller.agregarProducto("Laptop", "Laptop gaming", 1500.0, 5);
        
        InventarioController.ResultadoOperacion resultado = controller.actualizarProducto(1, "", "Descripción", 100.0, 5);
        
        assertFalse(resultado.isExito());
        assertTrue(resultado.getMensaje().contains("El nombre del producto no puede estar vacío"));
    }
    
    /**
     * Prueba actualizar producto inexistente
     */
    @Test
    public void testActualizarProductoInexistente() {
        InventarioController.ResultadoOperacion resultado = controller.actualizarProducto(999, "Nuevo", "Descripción", 100.0, 5);
        
        assertFalse(resultado.isExito());
        assertTrue(resultado.getMensaje().contains("No se encontró un producto con el ID"));
    }
    
    /**
     * Prueba buscar productos
     */
    @Test
    public void testBuscarProductos() {
        controller.agregarProducto("Laptop Dell", "Laptop Dell Inspiron", 1200.0, 5);
        controller.agregarProducto("Mouse Logitech", "Mouse inalámbrico", 25.99, 10);
        
        List<Producto> resultados = controller.buscarProductos("Dell");
        
        assertEquals(1, resultados.size());
        assertEquals("Laptop Dell", resultados.get(0).getNombre());
    }
    
    /**
     * Prueba buscar productos con criterio vacío
     */
    @Test
    public void testBuscarProductosCriterioVacio() {
        controller.agregarProducto("Laptop", "Descripción", 100.0, 5);
        
        List<Producto> resultados = controller.buscarProductos("");
        
        assertTrue(resultados.isEmpty());
    }
    
    /**
     * Prueba buscar producto por ID
     */
    @Test
    public void testBuscarProductoPorId() {
        controller.agregarProducto("Laptop", "Descripción", 100.0, 5);
        
        Producto producto = controller.buscarProductoPorId(1);
        
        assertNotNull(producto);
        assertEquals("Laptop", producto.getNombre());
    }
    
    /**
     * Prueba buscar producto por ID inexistente
     */
    @Test
    public void testBuscarProductoPorIdInexistente() {
        Producto producto = controller.buscarProductoPorId(999);
        
        assertNull(producto);
    }
    
    /**
     * Prueba obtener todos los productos
     */
    @Test
    public void testObtenerTodosLosProductos() {
        controller.agregarProducto("Producto 1", "Descripción 1", 100.0, 5);
        controller.agregarProducto("Producto 2", "Descripción 2", 200.0, 10);
        
        List<Producto> productos = controller.obtenerTodosLosProductos();
        
        assertEquals(2, productos.size());
    }
    
    /**
     * Prueba obtener total de productos
     */
    @Test
    public void testObtenerTotalProductos() {
        assertEquals(0, controller.obtenerTotalProductos());
        
        controller.agregarProducto("Producto 1", "Descripción 1", 100.0, 5);
        assertEquals(1, controller.obtenerTotalProductos());
        
        controller.agregarProducto("Producto 2", "Descripción 2", 200.0, 10);
        assertEquals(2, controller.obtenerTotalProductos());
    }
    
    /**
     * Prueba verificar si inventario está vacío
     */
    @Test
    public void testEstaInventarioVacio() {
        assertTrue(controller.estaInventarioVacio());
        
        controller.agregarProducto("Producto", "Descripción", 100.0, 5);
        assertFalse(controller.estaInventarioVacio());
    }
    
    /**
     * Prueba obtener siguiente ID
     */
    @Test
    public void testObtenerSiguienteId() {
        assertEquals(1, controller.obtenerSiguienteId());
        
        controller.agregarProducto("Producto 1", "Descripción 1", 100.0, 5);
        assertEquals(2, controller.obtenerSiguienteId());
    }
    
    /**
     * Prueba obtener resumen del inventario
     */
    @Test
    public void testObtenerResumenInventario() {
        controller.agregarProducto("Producto 1", "Descripción 1", 100.0, 5);
        controller.agregarProducto("Producto 2", "Descripción 2", 200.0, 3);
        
        InventarioController.ResumenInventario resumen = controller.obtenerResumenInventario();
        
        assertEquals(2, resumen.getTotalProductos());
        assertEquals(1100.0, resumen.getValorTotal(), 0.001); // (100*5) + (200*3) = 500 + 600 = 1100
        assertEquals(8, resumen.getTotalStock()); // 5 + 3 = 8
    }
    
    /**
     * Prueba obtener resumen de inventario vacío
     */
    @Test
    public void testObtenerResumenInventarioVacio() {
        InventarioController.ResumenInventario resumen = controller.obtenerResumenInventario();
        
        assertEquals(0, resumen.getTotalProductos());
        assertEquals(0.0, resumen.getValorTotal(), 0.001);
        assertEquals(0, resumen.getTotalStock());
    }
}
