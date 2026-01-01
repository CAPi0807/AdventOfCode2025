package software.aoc.day01.b;

public record Dial(int position) {

    public Dial {
        if (position < 0 || position >= 100) {
            throw new IllegalArgumentException("Position must be between 0 and 99");
        }
    }

    /**
     * Aplica la instrucción y devuelve el resultado compuesto (Nuevo Dial + Hits)
     */
    public TurnResult turn(Instruction instruction) {
        int hits = instruction.direction()
                .calculateHits(this.position, instruction.amount());

        int newPos = instruction.direction()
                .calculateNewPosition(this.position, instruction.amount());

        return new TurnResult(new Dial(newPos), hits);
    }
}