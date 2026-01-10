package software.aoc.day01.a.model;

public final class Dial {
    private final int position;

    public Dial(int position) {
        this.position = position;
    }

    public int position() {
        return position;
    }


    public Dial move(Instruction instruction) {
        int newPos = instruction.direction()
                .calculateNewPosition(this.position, instruction.amount());
        return new Dial(newPos);
    }

    public int calculateScore() {
        return (this.position == 0) ? 1 : 0;
    }
}