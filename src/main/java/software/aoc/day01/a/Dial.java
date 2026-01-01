package software.aoc.day01.a;

public final class Dial {
    private final int position;

    public Dial(int position) {
        this.position = position;
    }

    public int position() {
        return position;
    }

    /**
     * Aplica una instrucción y devuelve un nuevo estado del Dial.
     */
    public Dial move(Instruction instruction) {
        int newPos = instruction.direction()
                .calculateNewPosition(this.position, instruction.amount());
        return new Dial(newPos);
    }

    /**
     * Lógica de negocio específica: Evalúa si la posición actual contribuye a la contraseña.
     */
    public int calculateScore() {
        return (this.position == 0) ? 1 : 0;
    }
}