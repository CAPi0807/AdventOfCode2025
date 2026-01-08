package software.aoc.day01.b.service;

import software.aoc.day01.a.model.Instruction;
import software.aoc.day01.b.model.Dial;
import software.aoc.day01.b.model.TurnResult;

import java.util.List;

public class PasswordServiceB {

    public int calculatePassword(int startPosition, List<Instruction> instructions) {
        Dial currentDial = new Dial(startPosition);
        int totalHits = 0;

        for (Instruction instruction : instructions) {
            // Obtenemos el resultado de la transición
            TurnResult result = currentDial.turn(instruction);

            // Actualizamos estado y acumulamos hits
            currentDial = result.newDial();
            totalHits += result.hitsGenerated();
        }

        return totalHits;
    }
}