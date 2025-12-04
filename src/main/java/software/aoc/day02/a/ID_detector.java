package software.aoc.day02.a;

public class ID_detector {

    public static boolean isRepetitive(long number) {
        String s = String.valueOf(number);
        int len = s.length();

        // 1. Si la longitud es impar, no puede dividirse en dos partes iguales.
        // 2. Si es menor que 2 (un solo dígito), tampoco es una repetición.
        if (len % 2 != 0 || len < 2) {
            return false;
        }

        // Dividimos la cadena exactamente por la mitad
        String firstHalf = s.substring(0, len / 2);
        String secondHalf = s.substring(len / 2);

        // Comparamos si ambas mitades son idénticas
        return firstHalf.equals(secondHalf);
    }
}