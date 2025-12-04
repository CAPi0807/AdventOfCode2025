package software.aoc.day02.b;

import java.util.stream.IntStream;

public class ID_detector {

    public static boolean isRepetitive(long number) {
        String s = String.valueOf(number);
        int len = s.length();

        if (len < 2) return false;

        // La lógica interna sigue usando int para los índices del string
        return IntStream.rangeClosed(1, len / 2)
                .filter(chunkSize -> len % chunkSize == 0)
                .anyMatch(chunkSize -> {
                    String pattern = s.substring(0, chunkSize);
                    return s.replace(pattern, "").isEmpty();
                });
    }
}