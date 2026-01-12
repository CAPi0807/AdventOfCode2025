package software.aoc.day07.a.physics;

import software.aoc.day07.a.model.InteractionResult;

public class RayPhysics {

    private static final char divider = '^';

    public InteractionResult interact(char cellContent) {
        if (cellContent == divider) {
            return InteractionResult.split();
        }
        // 'S', '.', o cualquier otro carácter se comportan como "pasar recto"
        return InteractionResult.passThrough();
    }
}