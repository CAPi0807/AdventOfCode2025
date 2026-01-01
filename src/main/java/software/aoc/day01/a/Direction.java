package software.aoc.day01.a;

public enum Direction {
    LEFT {
        @Override
        public int calculateNewPosition(int currentPosition, int amount) {
            // (pos - val + 100) % 100 asegura el ciclo positivo
            return (currentPosition - amount + 100) % 100;
        }
    },
    RIGHT {
        @Override
        public int calculateNewPosition(int currentPosition, int amount) {
            return (currentPosition + amount) % 100;
        }
    };

    public abstract int calculateNewPosition(int currentPosition, int amount);

    public static Direction fromChar(char c) {
        return switch (c) {
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new IllegalArgumentException("Unknown direction: " + c);
        };
    }
}