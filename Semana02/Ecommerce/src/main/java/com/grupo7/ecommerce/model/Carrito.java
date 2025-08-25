package com.grupo7.ecommerce.model;

import java.util.*;
import java.util.stream.Collectors;

public class Carrito {
    private final List<Producto> items = new ArrayList<>();

    public void agregar(Producto p) {
        if (p != null) items.add(p);
    }

    public boolean eliminar(Producto p) {
        if (p == null) return false;
        for (Iterator<Producto> it = items.iterator(); it.hasNext();) {
            Producto curr = it.next();
            if (curr.getNombre().equalsIgnoreCase(p.getNombre())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public List<Producto> getItems() { return new ArrayList<>(items); }

    public double getTotal() {
        return items.stream().mapToDouble(Producto::getPrecio).sum();
    }

    public String resumen() {
        Map<String, Long> conteo = items.stream()
                .collect(Collectors.groupingBy(Producto::getNombre, LinkedHashMap::new, Collectors.counting()));
        StringBuilder sb = new StringBuilder();
        conteo.forEach((nombre, cantidad) -> sb.append("- ").append(nombre)
                .append(" x").append(cantidad).append('\n'));
        return sb.toString();
    }
}
