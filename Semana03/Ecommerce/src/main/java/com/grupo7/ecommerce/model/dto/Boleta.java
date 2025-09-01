package com.grupo7.ecommerce.model.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Boleta {
    private final List<LineaBoleta> lineas = new ArrayList<>();

    public void agregarLinea(LineaBoleta l) { if (l != null) lineas.add(l); }
    public List<LineaBoleta> getLineas() { return Collections.unmodifiableList(lineas); }

    public double getSubtotal() {
        return lineas.stream().mapToDouble(LineaBoleta::getPrecioBase).sum();
    }
    public double getTotal() {
        return lineas.stream().mapToDouble(LineaBoleta::getPrecioFinal).sum();
    }
    public double getAhorroTotal() {
        return Math.max(0, getSubtotal() - getTotal());
    }
}

