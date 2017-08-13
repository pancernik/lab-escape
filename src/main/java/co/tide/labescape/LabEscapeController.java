package co.tide.labescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class LabEscapeController {

    public static final String NL = "\n";

    @RequestMapping(value = "/lab-escape", method = POST)
    @ResponseBody
    public String labEscape(
            @RequestBody String labyrinth,
            @RequestParam int startX,
            @RequestParam int startY) {

        char[][] labyrinthWithEscape = LabEscape.drawPathForEscape(asCharArray(labyrinth), startX, startY);

        return asString(labyrinthWithEscape);
    }

    private char[][] asCharArray(String labyrinthAsString) {
        return Pattern.compile(NL)
                .splitAsStream(labyrinthAsString)
                .map(String::toCharArray)
                .toArray(char[][]::new);
    }

    private String asString(char[][] labyrinthWithEscape) {
        return Arrays.stream(labyrinthWithEscape)
                .map(String::valueOf)
                .collect(Collectors.joining(NL));
    }

    @ExceptionHandler(NoEscapeException.class)
    @ResponseStatus(OK)
    @ResponseBody
    public String noEscapeHandler() {
        return "No escape";
    }

}
