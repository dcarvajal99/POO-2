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
 * Pruebas de integración para verificar la integración entre Model, View y Controller
 * Verifica que el controlador maneje correctamente las operaciones del inventario
 * 
 *@author grupo7
 */
public class PruebasIntegracionMVCTest {
    
    private InventarioController controller;
    private Inventario inventario;
    
    @BeforeEach
    public void setUp() {
        inventario = new Inventario();
        controller = new InventarioController(inventario);
    }
    
    /**
     * Prueba de integración: Agregar productos y verificar coherencia del inventario
     */
    @Test
    public void testIntegracionAgregarProductos() {
        // Agregar productos al inventario usando el controlador
        InventarioController.ResultadoOperacion resultado1 = controller.agregarProducto("Laptop Dell", "Laptop Dell Inspiron 15", 1200.0, 8);
        InventarioController.ResultadoOperacion resultado2 = controller.agregarProducto("Mouse Logitech", "Mouse inalámbrico Logitech", 35.99, 25);
        InventarioController.ResultadoOperacion resultado3 = controller.agregarProducto("Teclado Razer", "Teclado mecánico Razer", 129.99, 12);
        
        // Verificar que todas las operaciones fueron exitosas
        assertTrue(resultado1.isExito());
        assertTrue(resultado2.isExito());
        assertTrue(resultado3.isExito());
        
        // Verificar coherencia del estado del inventario
        assertEquals(3, controller.obtenerTotalProductos());
        assertFalse(controller.estaInventarioVacio());
        
        // Verificar que todos los productos están en el inventario
        List<Producto> todosLosProductos = controller.obtenerTodosLosProductos();
        assertEquals(3, todosLosProductos.size());
        
        // Verificar que se pueden buscar por ID
        assertNotNull(controller.buscarProductoPorId(1));
        assertNotNull(controller.buscarProductoPorId(2));
        assertNotNull(controller.buscarProductoPorId(3));
    }
    
    /**
     * Prueba de integración: Buscar productos y verificar resultados correctos
     */
    @Test
    public void testIntegracionBuscarProductos() {
        // Agregar productos al inventario
        controller.agregarProducto("Laptop Dell", "Laptop Dell Inspiron 15", 1200.0, 8);
        controller.agregarProducto("Mouse Logitech", "Mouse inalámbrico Logitech", 35.99, 25);
        controller.agregarProducto("Teclado Razer", "Teclado mecánico Razer", 129.99, 12);
        
        // Buscar por nombre específico
        List<Producto> resultadosNombre = controller.buscarProductos("Laptop");
        assertEquals(1, resultadosNombre.size());
        assertTrue(resultadosNombre.get(0).getNombre().contains("Laptop"));
        
        // Buscar por descripción
        List<Producto> resultadosDescripcion = controller.buscarProductos("inalámbrico");
        assertEquals(1, resultadosDescripcion.size());
        assertTrue(resultadosDescripcion.get(0).getDescripcion().contains("inalámbrico"));
        
        // Buscar por criterio general
        List<Producto> resultadosGenerales = controller.buscarProductos("Razer");
        assertEquals(1, resultadosGenerales.size());
        assertTrue(resultadosGenerales.get(0).getNombre().contains("Razer"));
        
        // Buscar algo que no existe
        List<Producto> resultadosNoExiste = controller.buscarProductos("Tablet");
        assertTrue(resultadosNoExiste.isEmpty());
    }
    
    /**
     * Prueba de integración: Eliminar productos y verificar coherencia del inventario
     */
    @Test
    public void testIntegracionEliminarProductos() {
        // Agregar productos al inventario
        controller.agregarProducto("Laptop Dell", "Laptop Dell Inspiron 15", 1200.0, 8);
        controller.agregarProducto("Mouse Logitech", "Mouse inalámbrico Logitech", 35.99, 25);
        controller.agregarProducto("Teclado Razer", "Teclado mecánico Razer", 129.99, 12);
        
        // Verificar estado inicial
        assertEquals(3, controller.obtenerTotalProductos());
        
        // Eliminar producto por ID
        InventarioController.ResultadoOperacion resultado = controller.eliminarProducto(2);
        assertTrue(resultado.isExito());
        
        // Verificar coherencia después de la eliminación
        assertEquals(2, controller.obtenerTotalProductos());
        assertNull(controller.buscarProductoPorId(2));
        assertNotNull(controller.buscarProductoPorId(1));
        assertNotNull(controller.buscarProductoPorId(3));
        
        // Verificar que el producto eliminado no aparece en búsquedas
        List<Producto> resultados = controller.buscarProductos("Mouse");
        assertTrue(resultados.isEmpty());
        
        // Eliminar otro producto
        resultado = controller.eliminarProducto(1);
        assertTrue(resultado.isExito());
        assertEquals(1, controller.obtenerTotalProductos());
        
        // Verificar que solo queda el producto 3
        List<Producto> productosRestantes = controller.obtenerTodosLosProductos();
        assertEquals(1, productosRestantes.size());
        assertEquals(3, productosRestantes.get(0).getId());
    }
    
    /**
     * Prueba de integración: Actualizar productos y verificar cambios
     */
    @Test
    public void testIntegracionActualizarProductos() {
        // Agregar productos al inventario
        controller.agregarProducto("Laptop Dell", "Laptop Dell Inspiron 15", 1200.0, 8);
        controller.agregarProducto("Mouse Logitech", "Mouse inalámbrico Logitech", 35.99, 25);
        
        // Actualizar producto existente
        InventarioController.ResultadoOperacion resultado = controller.actualizarProducto(1, "Laptop HP", "Laptop HP Pavilion", 1100.0, 6);
        assertTrue(resultado.isExito());
        
        // Verificar que los cambios se reflejan correctamente
        Producto productoActualizado = controller.buscarProductoPorId(1);
        assertEquals("Laptop HP", productoActualizado.getNombre());
        assertEquals("Laptop HP Pavilion", productoActualizado.getDescripcion());
        assertEquals(1100.0, productoActualizado.getPrecio(), 0.001);
        assertEquals(6, productoActualizado.getCantidadStock());
        
        // Verificar que las búsquedas funcionan con los nuevos datos
        List<Producto> resultados = controller.buscarProductos("HP");
        assertEquals(1, resultados.size());
        assertTrue(resultados.contains(productoActualizado));
        
        // Verificar que el otro producto no se vio afectado
        Producto productoNoModificado = controller.buscarProductoPorId(2);
        assertEquals("Mouse Logitech", productoNoModificado.getNombre());
    }
    
    /**
     * Prueba de integración: Flujo completo de operaciones
     */
    @Test
    public void testIntegracionFlujoCompleto() {
        // 1. Inventario vacío inicial
        assertTrue(controller.estaInventarioVacio());
        assertEquals(0, controller.obtenerTotalProductos());
        
        // 2. Agregar productos
        InventarioController.ResultadoOperacion resultado1 = controller.agregarProducto("Laptop Dell", "Laptop Dell Inspiron 15", 1200.0, 8);
        InventarioController.ResultadoOperacion resultado2 = controller.agregarProducto("Mouse Logitech", "Mouse inalámbrico Logitech", 35.99, 25);
        InventarioController.ResultadoOperacion resultado3 = controller.agregarProducto("Teclado Razer", "Teclado mecánico Razer", 129.99, 12);
        
        assertTrue(resultado1.isExito());
        assertTrue(resultado2.isExito());
        assertTrue(resultado3.isExito());
        
        // 3. Verificar agregación
        assertEquals(3, controller.obtenerTotalProductos());
        assertFalse(controller.estaInventarioVacio());
        
        // 4. Buscar productos
        List<Producto> resultadosBusqueda = controller.buscarProductos("Dell");
        assertEquals(1, resultadosBusqueda.size());
        
        // 5. Actualizar producto
        InventarioController.ResultadoOperacion resultadoActualizacion = controller.actualizarProducto(1, "Laptop Dell Actualizada", "Laptop Dell Inspiron 15 Actualizada", 1300.0, 10);
        assertTrue(resultadoActualizacion.isExito());
        
        // 6. Verificar actualización
        Producto productoActualizado = controller.buscarProductoPorId(1);
        assertEquals("Laptop Dell Actualizada", productoActualizado.getNombre());
        assertEquals(1300.0, productoActualizado.getPrecio(), 0.001);
        
        // 7. Eliminar producto
        InventarioController.ResultadoOperacion resultadoEliminacion = controller.eliminarProducto(2);
        assertTrue(resultadoEliminacion.isExito());
        
        // 8. Verificar eliminación
        assertEquals(2, controller.obtenerTotalProductos());
        assertNull(controller.buscarProductoPorId(2));
        
        // 9. Verificar estado final
        List<Producto> productosFinales = controller.obtenerTodosLosProductos();
        assertEquals(2, productosFinales.size());
        assertTrue(productosFinales.contains(productoActualizado));
        assertNotNull(controller.buscarProductoPorId(3));
    }
    
    /**
     * Prueba de integración: Manejo de casos límite
     */
    @Test
    public void testIntegracionCasosLimite() {
        // Agregar productos
        controller.agregarProducto("Laptop Dell", "Laptop Dell Inspiron 15", 1200.0, 8);
        controller.agregarProducto("Mouse Logitech", "Mouse inalámbrico Logitech", 35.99, 25);
        
        // Intentar eliminar producto inexistente
        InventarioController.ResultadoOperacion resultado = controller.eliminarProducto(999);
        assertFalse(resultado.isExito());
        assertEquals(2, controller.obtenerTotalProductos());
        
        // Intentar actualizar producto inexistente
        InventarioController.ResultadoOperacion resultadoActualizacion = controller.actualizarProducto(999, "Nuevo", "Nueva descripción", 100.0, 1);
        assertFalse(resultadoActualizacion.isExito());
        
        // Verificar que los productos originales no cambiaron
        assertEquals(2, controller.obtenerTotalProductos());
        assertNotNull(controller.buscarProductoPorId(1));
        assertNotNull(controller.buscarProductoPorId(2));
        
        // Buscar con criterio vacío
        List<Producto> resultadosVacios = controller.buscarProductos("");
        assertTrue(resultadosVacios.isEmpty());
        
        // Buscar con criterio nulo
        List<Producto> resultadosNulos = controller.buscarProductos(null);
        assertTrue(resultadosNulos.isEmpty());
    }
    
    /**
     * Prueba de integración: Verificar resumen del inventario
     */
    @Test
    public void testIntegracionResumenInventario() {
        // Agregar productos
        controller.agregarProducto("Laptop Dell", "Laptop Dell Inspiron 15", 1200.0, 8);
        controller.agregarProducto("Mouse Logitech", "Mouse inalámbrico Logitech", 35.99, 25);
        controller.agregarProducto("Teclado Razer", "Teclado mecánico Razer", 129.99, 12);
        
        // Obtener resumen
        InventarioController.ResumenInventario resumen = controller.obtenerResumenInventario();
        
        // Verificar valores del resumen
        assertEquals(3, resumen.getTotalProductos());
        assertEquals(8 + 25 + 12, resumen.getTotalStock()); // 45 unidades totales
        
        // Calcular valor total esperado: (1200*8) + (35.99*25) + (129.99*12) = 9600 + 899.75 + 1559.88 = 12059.63
        double valorEsperado = (1200.0 * 8) + (35.99 * 25) + (129.99 * 12);
        assertEquals(valorEsperado, resumen.getValorTotal(), 0.01);
    }
}

