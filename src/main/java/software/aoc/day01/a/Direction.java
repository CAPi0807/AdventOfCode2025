package software.aoc.day01.a;

public enum Direction {
    LEFT,
    RIGHT;

    public static Direction fromChar(char c) {
        return switch (c) {
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new IllegalArgumentException("Unknown direction: " + c);
        };
    }

    // Puedes dejar calculateNewPosition aquí si quieres que A lo use,
    // pero B usará su propia lógica.
    public int calculateNewPosition(int currentPosition, int amount) {
        if (this == LEFT) {
            return Math.floorMod(currentPosition - amount, 100);
        } else {
            return (currentPosition + amount) % 100;
        }
    }
}