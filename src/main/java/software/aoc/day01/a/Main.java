package software.aoc.day01.a;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {

        // 1. Leemos las órdenes del archivo
        List<String> orders = Files.readAllLines(
                Path.of("src/main/resources/Day01/Orders.txt")
        );

        // 2. Creamos el dial arrancando en 50
        Dial dial = new Dial(50);

        // 3. Aplicamos las órdenes y obtenemos la contraseña
        int password = dial.applyOrders(
                orders.stream()
                        .map(String::trim)
                        .filter(l -> !l.isEmpty())
                        .toList()
        );

        // 4. Mostramos la contraseña
        System.out.println("Contraseña = " + password);
    }
}
