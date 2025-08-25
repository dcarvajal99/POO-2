package com.grupo7.ecommerce.commands;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private final List<Command> comandos = new ArrayList<>();
    public void agregarComando(Command comando) {
        comandos.add(comando);
    }
    public void ejecutarComandos() {
        for (Command comando : comandos) {
            comando.ejecutar();
        }
        comandos.clear();
    }
}