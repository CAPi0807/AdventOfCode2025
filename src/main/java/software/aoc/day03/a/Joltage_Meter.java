package software.aoc.day03.a;

import java.util.List;

public class Joltage_Meter {

    public static int JoltajeDeterminator(List<String> batteries) {

        return batteries.stream()
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToInt(Joltage_Meter::highestOrderedPair)
                .sum();
    }

    private static int highestOrderedPair(String s) {
        int max = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            int firstDigit = s.charAt(i) - '0'; //Esto cambia el valor Ascii por su valor int

            for (int j = i + 1; j < s.length(); j++) {
                int secondDigit = s.charAt(j) - '0';

                int pairValue = firstDigit * 10 + secondDigit;

                if (pairValue > max) {
                    max = pairValue;
                    // el máximo posible es 99; si llegamos ahí, no hace falta seguir buscando
                    if (max == 99) return max;
                }
            }
        }
        return max;
    }
}
