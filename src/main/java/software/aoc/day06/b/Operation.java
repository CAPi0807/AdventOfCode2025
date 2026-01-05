package software.aoc.day06.b;

import java.util.function.LongBinaryOperator;

public enum Operation {
    SUM((a, b) -> a + b, 0),
    MULTIPLY((a, b) -> a * b, 1);

    private final LongBinaryOperator function;
    private final long identity;

    Operation(LongBinaryOperator function, long identity) {
        this.function = function;
        this.identity = identity;
    }

    public long apply(long a, long b) {
        return function.applyAsLong(a, b);
    }

    public long identity() {
        return identity;
    }

    /**
     * Regla de negocio crítica:
     * Si la "firma" de operadores del bloque contiene un '*', todo el bloque es multiplicación.
     * En caso contrario, es suma.
     */
    public static Operation determineFromSignature(String signature) {
        return signature.contains("*") ? MULTIPLY : SUM;
    }
}