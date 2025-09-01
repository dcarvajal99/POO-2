package com.grupo7.ecommerce.model;

import java.util.*;
import java.util.stream.Collectors;

public class Carrito {
    private final List<Component> items = new ArrayList<>();

    public void agregar(Component p) {
        if (p != null) items.add(p);
    }

    public boolean eliminar(Component p) {
        if (p == null) return false;
        for (Iterator<Component> it = items.iterator(); it.hasNext();) {
            Component curr = it.next();
            if (curr.getNombre().equalsIgnoreCase(p.getNombre())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public List<Component> getItems() { return new ArrayList<>(items); }

    public double getTotal() {
        return items.stream().mapToDouble(Component::getPrecio).sum();
    }

    public String resumen() {
        Map<String, Long> conteo = items.stream()
                .collect(Collectors.groupingBy(Component::getNombre, LinkedHashMap::new, Collectors.counting()));
        StringBuilder sb = new StringBuilder();
        conteo.forEach((nombre, cantidad) -> sb.append("- ").append(nombre)
                .append(" x").append(cantidad).append('\n'));
        return sb.toString();
    }

    // Nuevo: vaciar el carrito en memoria
    public void clear() {
        items.clear();
    }
}
