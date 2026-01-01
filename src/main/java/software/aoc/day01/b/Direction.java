package software.aoc.day01.b;

public enum Direction {
    RIGHT {
        @Override
        public int calculateHits(int currentPosition, int amount) {
            // Lógica original: (position + value) / SIZE
            return (currentPosition + amount) / 100;
        }

        @Override
        public int calculateNewPosition(int currentPosition, int amount) {
            return (currentPosition + amount) % 100;
        }
    },
    LEFT {
        @Override
        public int calculateHits(int currentPosition, int amount) {
            // Distancia para tocar el 0 yendo hacia atrás.
            // Si pos es 0, necesito 100 para volver a tocarlo (vuelta completa).
            int distToZero = (currentPosition == 0) ? 100 : currentPosition;

            if (amount >= distToZero) {
                // 1 hit por llegar a cero + hits extra por cada 100 pasos restantes
                return 1 + (amount - distToZero) / 100;
            }
            return 0;
        }

        @Override
        public int calculateNewPosition(int currentPosition, int amount) {
            // Math.floorMod maneja correctamente los negativos (ej: -150 % 100 = 50)
            return Math.floorMod(currentPosition - amount, 100);
        }
    };

    public abstract int calculateHits(int currentPosition, int amount);
    public abstract int calculateNewPosition(int currentPosition, int amount);

    public static Direction fromChar(char c) {
        return switch (c) {
            case 'L' -> LEFT;
            case 'R' -> RIGHT;
            default -> throw new IllegalArgumentException("Unknown direction: " + c);
        };
    }
}