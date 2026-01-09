package software.aoc.day06.a.model;

import java.util.Arrays;

public enum Operator {
    ADD('+') {
        @Override
        public long apply(long acc, long value) {
            return acc + value;
        }

        @Override
        public long identity() {
            return 0; // 0 + x = x
        }
    },
    MULTIPLY('*') {
        @Override
        public long apply(long acc, long value) {
            return acc * value;
        }

        @Override
        public long identity() {
            return 1; // 1 * x = x
        }
    };

    private final char symbol;

    Operator(char symbol) {
        this.symbol = symbol;
    }

    public abstract long apply(long acc, long value);
    public abstract long identity();

    public static Operator fromChar(int c) {
        return Arrays.stream(values())
                .filter(op -> op.symbol == (char) c)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown operator: " + (char) c));
    }

    public static boolean isOperatorChar(int c) {
        return c == '+' || c == '*';
    }
}