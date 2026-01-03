package software.aoc.day02.a;

public class RepetitiveNumberPredicate implements NumberPredicate {

    @Override
    public boolean test(long number) {
        String s = String.valueOf(number);
        int len = s.length();

        // Reglas de negocio: longitud par y al menos 2 dígitos
        if (len < 2 || len % 2 != 0) {
            return false;
        }

        int mid = len / 2;
        String firstHalf = s.substring(0, mid);
        String secondHalf = s.substring(mid);

        return firstHalf.equals(secondHalf);
    }
}