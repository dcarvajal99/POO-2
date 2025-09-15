/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.grupo7.gestioninventariojava;

import com.grupo7.gestioninventariojava.view.MenuPrincipal;

/**
 * Clase principal de la aplicación de gestión de inventario
 * Implementa la arquitectura MVC
 * 
 *@author grupo7
 */
public class GestionInventarioJava {

    /**
     * Método principal que inicia la aplicación
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        MenuPrincipal vista = new MenuPrincipal();
        vista.ejecutar();
    }
}
