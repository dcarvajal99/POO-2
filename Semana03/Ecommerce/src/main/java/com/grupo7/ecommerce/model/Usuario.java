package com.grupo7.ecommerce.model;

public class Usuario {
    private final String id;
    private final String nombre;
    private final String email;

    public Usuario(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
}

