package software.aoc.day01.a;

import java.util.List;

public class Dial {
    private int position;

    public Dial(int start) {
        this.position = start;
    }

    public int applyOrders(List<String> orders) {
        return orders.stream()
                .mapToInt(this::applySingleOrder)
                .sum();
    }

    private int applySingleOrder(String order) {
        int value = Integer.parseInt(order.substring(1));
        char dir = order.charAt(0);

        position = switch (dir) {
            case 'L' -> (position - value + 100) % 100;
            case 'R' -> (position + value) % 100;
            default -> throw new IllegalArgumentException("Orden inválida: " + order);
        };

        if (position == 0) {
            return 1;
        } else {
            return 0;
        }
    }
}

