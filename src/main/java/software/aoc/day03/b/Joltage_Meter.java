package software.aoc.day03.b;

import java.util.List;

public class Joltage_Meter {

    private static final int LENGTH = 12; // número de dígitos a seleccionar
    private static final String MAX_COMBO = "9".repeat(LENGTH);

    public static long JoltajeDeterminator(List<String> batteries) {
        return batteries.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToLong(Joltage_Meter::maxComboOf12)
                .sum();
    }

    private static long maxComboOf12(String s) {
        int n = s.length();
        if (n < LENGTH) return 0; // no es posible construir 12 dígitos

        char[] result = new char[LENGTH];
        int pos = 0;
        int start = 0; // punto inicial en la ristra para buscar el siguiente dígito

        while (pos < LENGTH) {
            char best = '0';

            int remaining = LENGTH - pos - 1;
            int limit = n - remaining;

            for (int i = start; i < limit; i++) {
                char c = s.charAt(i);
                if (c > best) best = c;

                // si encontramos un '9', ya no habrá nada mejor
                if (best == '9') break;
            }

            result[pos] = best;

            // mover start detrás de donde encontramos 'best'
            start = s.indexOf(best, start) + 1;

            pos++;
        }

        String combo = new String(result);

        // Si es la combinación máxima posible, devolvemos directamente
        if (combo.equals(MAX_COMBO)) return Long.parseLong(combo);

        return Long.parseLong(combo);
    }
}
