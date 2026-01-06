package software.aoc.day10.a;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MachineParser {

    private static final Pattern LIGHTS = Pattern.compile("\\[([.#]+)]");
    private static final Pattern BUTTONS = Pattern.compile("\\(([,\\d]+)\\)");
    private static final Pattern VOLTAGE = Pattern.compile("\\{([,\\d]+)}");

    public Machine parse(String line) {
        return new Machine(
                parseSequence(line, LIGHTS, true),
                parseButtons(line),
                parseSequence(line, VOLTAGE, false)
        );
    }

    private List<Integer> parseSequence(String line, Pattern pattern, boolean mapChars) {
        Matcher m = pattern.matcher(line);
        if (m.find()) {
            String content = m.group(1);
            if (mapChars) { // Para [.##.]
                return content.chars().mapToObj(c -> c == '#' ? 1 : 0).toList();
            } else { // Para {3,5,4}
                return Stream.of(content.split(","))
                        .map(String::trim)
                        .map(Integer::parseInt)
                        .toList();
            }
        }
        return List.of();
    }

    private List<List<Integer>> parseButtons(String line) {
        Matcher m = BUTTONS.matcher(line);
        List<List<Integer>> buttons = new ArrayList<>();
        while (m.find()) {
            buttons.add(Stream.of(m.group(1).split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList());
        }
        return buttons;
    }
}
