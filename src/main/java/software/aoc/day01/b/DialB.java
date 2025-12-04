package software.aoc.day01.b;

import java.util.List;

public class DialB {
    private int position;
    private final int SIZE = 100; // Constante para claridad

    public DialB(int start) {
        this.position = start;
    }

    public int applyOrdersB(List<String> orders) {
        return orders.stream()
                .mapToInt(this::applySingleOrder)
                .sum();
    }

    private int applySingleOrder(String order) {
        int value = Integer.parseInt(order.substring(1));
        char dir = order.charAt(0);

        int hits = 0;

        if (dir == 'R') {
            // CALCULO DE PASOS POR CERO (DERECHA)
            // Si estoy en 90 y sumo 20 (total 110), 110/100 = 1 hit.
            hits = (position + value) / SIZE;

            // Actualizar posición
            position = (position + value) % SIZE;

        } else if (dir == 'L') {
            // CALCULO DE PASOS POR CERO (IZQUIERDA)
            // Distancia para tocar el 0 yendo hacia atrás.
            // Si estoy en 0, necesito 100 para volver a tocarlo. Si estoy en 10, necesito 10.
            int distToZero = (position == 0) ? SIZE : position;

            if (value >= distToZero) {
                // Primer hit al llegar a 0 + hits adicionales por cada vuelta completa
                hits = 1 + (value - distToZero) / SIZE;
            }

            // Actualizar posición (Manejo seguro de negativos para giros grandes)
            // Math.floorMod maneja correctamente -150 % 100 = 50
            position = Math.floorMod(position - value, SIZE);

        } else {
            throw new IllegalArgumentException("Orden inválida: " + order);
        }

        return hits;
    }
}