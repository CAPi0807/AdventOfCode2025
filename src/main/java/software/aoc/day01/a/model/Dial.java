package software.aoc.day01.a.model;

public record Dial(int position) {

    public Dial move(Instruction instruction) {
        int newPos = instruction.direction()
                .calculateNewPosition(this.position, instruction.amount());
        return new Dial(newPos);
    }

    public int calculateScore() {
        return (this.position == 0) ? 1 : 0;
    }
}