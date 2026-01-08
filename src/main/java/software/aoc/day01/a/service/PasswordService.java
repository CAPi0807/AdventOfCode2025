package software.aoc.day01.a.service;

import software.aoc.day01.a.model.Dial;
import software.aoc.day01.a.model.Instruction;

import java.util.List;

public class PasswordService {

    public int calculatePassword(int startPosition, List<Instruction> instructions) {
        Dial currentDial = new Dial(startPosition);
        int passwordScore = 0;

        for (Instruction instruction : instructions) {
            // Inmutabilidad: Obtenemos una nueva instancia
            currentDial = currentDial.move(instruction);

            // Acumulamos el resultado basado en el nuevo estado
            passwordScore += currentDial.calculateScore();
        }

        return passwordScore;
    }
}