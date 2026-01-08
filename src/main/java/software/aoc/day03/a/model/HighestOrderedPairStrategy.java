package software.aoc.day03.a.model;

import software.aoc.day03.a.strategies.JoltageStrategy;

public class HighestOrderedPairStrategy implements JoltageStrategy {

    @Override
    public long calculate(Battery battery) {
        String s = battery.sequence();
        int max = 0;
        int length = s.length();

        for (int i = 0; i < length - 1; i++) {
            // Conversión optimizada de char a int ('5' - '0' = 5)
            int firstDigit = s.charAt(i) - '0';

            for (int j = i + 1; j < length; j++) {
                int secondDigit = s.charAt(j) - '0';

                // Formamos el número de dos dígitos
                int pairValue = firstDigit * 10 + secondDigit;

                if (pairValue > max) {
                    max = pairValue;
                    // Short-circuit: 99 es el máximo matemático posible
                    if (max == 99) return max;
                }
            }
        }
        return max;
    }
}