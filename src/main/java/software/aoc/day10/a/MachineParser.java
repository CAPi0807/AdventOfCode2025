package software.aoc.day10.a;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MachineParser {

    // Regex para capturar: [luces], (botones), {voltaje ignorado}
    private static final Pattern LIGHTS_PATTERN = Pattern.compile("\\[([.#]+)]");
    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\(([,\\d]+)\\)");

    public Machine parse(String line) {
        List<Integer> lights = parseLights(line);
        List<List<Integer>> buttons = parseButtons(line);
        return new Machine(lights, buttons);
    }

    private List<Integer> parseLights(String line) {
        Matcher matcher = LIGHTS_PATTERN.matcher(line);
        if (matcher.find()) {
            String content = matcher.group(1);
            return content.chars()
                    .mapToObj(c -> c == '#' ? 1 : 0)
                    .collect(Collectors.toList());
        }
        throw new IllegalArgumentException("No lights found in: " + line);
    }

    private List<List<Integer>> parseButtons(String line) {
        Matcher matcher = BUTTON_PATTERN.matcher(line);
        List<List<Integer>> buttons = new ArrayList<>();

        while (matcher.find()) {
            String content = matcher.group(1);
            List<Integer> indices = Stream.of(content.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            buttons.add(indices);
        }
        return buttons;
    }
}
