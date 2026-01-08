package software.aoc.day02.b.model;

import software.aoc.day02.a.model.NumberPredicate;

import java.util.stream.IntStream;

public class PatternRepetitivePredicate implements NumberPredicate {

    @Override
    public boolean test(long number) {
        String s = String.valueOf(number);
        int len = s.length();

        if (len < 2) return false;

        // Buscamos cualquier patrón divisor (chunkSize) que pueda formar la cadena completa.
        // Ej: Para "123123", probamos chunk 1 ("1"), chunk 2 ("12"), chunk 3 ("123")...
        return IntStream.rangeClosed(1, len / 2)
                .filter(chunkSize -> len % chunkSize == 0) // Optimización: Solo divisores exactos
                .anyMatch(chunkSize -> {
                    String pattern = s.substring(0, chunkSize);
                    // Si al eliminar todas las ocurrencias del patrón queda vacío, es repetitivo.
                    return s.replace(pattern, "").isEmpty();
                });
    }
}