package software.aoc.day03.b;

import software.aoc.day03.a.Battery;
import software.aoc.day03.a.JoltageStrategy;

public class GreedySelectionStrategy implements JoltageStrategy {

    private static final int TARGET_LENGTH = 12;

    @Override
    public long calculate(Battery battery) {
        String s = battery.sequence();
        int n = s.length();

        // Regla de negocio: Si no hay suficientes dígitos, devolvemos 0
        if (n < TARGET_LENGTH) {
            return 0;
        }

        StringBuilder result = new StringBuilder(TARGET_LENGTH);
        int currentSearchIndex = 0; // "start" en el código original

        // Necesitamos encontrar 12 dígitos
        for (int digitsCollected = 0; digitsCollected < TARGET_LENGTH; digitsCollected++) {

            // Calculamos hasta dónde podemos buscar para asegurar que queden suficientes caracteres
            // para completar los 12 dígitos.
            int remainingNeeded = TARGET_LENGTH - 1 - digitsCollected;
            int searchLimit = n - remainingNeeded;

            char bestChar = '/'; // Ascii menor que '0'
            int bestCharIndex = -1;

            // Búsqueda voraz en la ventana disponible
            for (int i = currentSearchIndex; i < searchLimit; i++) {
                char c = s.charAt(i);

                // Buscamos el mayor dígito. Si hay empate, nos quedamos con el primero (c > bestChar)
                if (c > bestChar) {
                    bestChar = c;
                    bestCharIndex = i;

                    // Optimización: Si encontramos un '9', es imposible mejorar.
                    if (bestChar == '9') break;
                }
            }

            result.append(bestChar);

            // Avanzamos el puntero justo después del dígito que acabamos de seleccionar
            currentSearchIndex = bestCharIndex + 1;
        }

        return Long.parseLong(result.toString());
    }
}