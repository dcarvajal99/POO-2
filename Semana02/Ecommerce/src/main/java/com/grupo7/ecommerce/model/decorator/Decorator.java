package com.grupo7.ecommerce.model.decorator;

import com.grupo7.ecommerce.model.Component;

public abstract class Decorator implements Component {
    protected final Component component;
    public Decorator(Component component) {
        this.component = component;
    }
    @Override public String getNombre() { return component.getNombre(); }
    @Override public String getCategoria() { return component.getCategoria(); }
    @Override public double getPrecio() { return component.getPrecio(); }
}
