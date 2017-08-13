package co.tide.labescape;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class LabEscapeTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void finds_simple_escape_path() {
        // given
        int startX = 1;
        int startY = 1;

        char[][] labyrinth = $$(
                $('O', 'O', 'O', 'O'),
                $('O', ' ', ' ', ' '),
                $('O', 'O', 'O', 'O'),
                $('O', 'O', 'O', 'O')
        );

        // when
        char[][] result = LabEscape.drawPathForEscape(labyrinth, startX, startY);

        // then
        assertThat(result).isEqualTo($$(
                $('O', 'O', 'O', 'O'),
                $('O', '•', '•', '•'),
                $('O', 'O', 'O', 'O'),
                $('O', 'O', 'O', 'O')
        ));
    }

    @Test
    public void finds_complex_escape_path() {
        // given
        int startX = 3;
        int startY = 1;

        char[][] labyrinth = $$(
                $('O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'),
                $('O', ' ', ' ', ' ', ' ', 'O', ' ', ' ', ' ', 'O'),
                $('O', ' ', 'O', 'O', ' ', 'O', ' ', 'O', ' ', 'O'),
                $('O', ' ', ' ', 'O', ' ', 'O', ' ', 'O', ' ', 'O'),
                $('O', ' ', 'O', 'O', ' ', ' ', ' ', 'O', ' ', ' '),
                $('O', ' ', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'),
                $('O', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'O'),
                $('O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O')
        );

        char[][] result = LabEscape.drawPathForEscape(labyrinth, startX, startY);

        // then
        assertThat(result).isEqualTo($$(
                $('O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'),
                $('O', '•', '•', '•', '•', 'O', '•', '•', '•', 'O'),
                $('O', '•', 'O', 'O', '•', 'O', '•', 'O', '•', 'O'),
                $('O', '•', ' ', 'O', '•', 'O', '•', 'O', '•', 'O'),
                $('O', ' ', 'O', 'O', '•', '•', '•', 'O', '•', '•'),
                $('O', ' ', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O'),
                $('O', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'O'),
                $('O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'O')
        ));
    }

    @Test
    public void finds_one_of_multiple_escape_paths() {
        // given
        int startX = 1;
        int startY = 1;

        char[][] labyrinth = $$(
                $('O', 'O', 'O', 'O'),
                $('O', ' ', ' ', ' '),
                $('O', ' ', 'O', 'O'),
                $('O', ' ', 'O', 'O')
        );

        // when
        char[][] result = LabEscape.drawPathForEscape(labyrinth, startX, startY);

        // then
        assertThat(result).isEqualTo($$(
                $('O', 'O', 'O', 'O'),
                $('O', '•', ' ', ' '),
                $('O', '•', 'O', 'O'),
                $('O', '•', 'O', 'O')
        ));
    }

    @Test
    public void finds_shortest_of_multiple_escape_paths() {
        // given
        int startX = 1;
        int startY = 2;

        char[][] labyrinth = $$(
                $('O', 'O', 'O', 'O'),
                $('O', 'O', ' ', ' '),
                $('O', 'O', ' ', 'O'),
                $('O', 'O', ' ', 'O')
        );

        // when
        char[][] result = LabEscape.drawPathForEscape(labyrinth, startX, startY);

        // then
        assertThat(result).isEqualTo($$(
                $('O', 'O', 'O', 'O'),
                $('O', 'O', '•', '•'),
                $('O', 'O', ' ', 'O'),
                $('O', 'O', ' ', 'O')
        ));
    }

    @Test
    public void finds_escape_path_from_open_starting_point() {
        // given
        int startX = 2;
        int startY = 2;

        char[][] labyrinth = $$(
                $('O', 'O', 'O', 'O'),
                $('O', ' ', ' ', ' '),
                $('O', ' ', ' ', ' '),
                $('O', ' ', ' ', ' ')
        );

        // when
        char[][] result = LabEscape.drawPathForEscape(labyrinth, startX, startY);

        // then
        assertThat(result).isEqualTo($$(
                $('O', 'O', 'O', 'O'),
                $('O', ' ', ' ', ' '),
                $('O', ' ', '•', ' '),
                $('O', ' ', '•', ' ')
        ));
    }

    @Test
    public void finds_escape_path_if_there_a_cycle() {
        // given
        int startX = 1;
        int startY = 2;

        char[][] labyrinth = $$(
                $('O', 'O', 'O', 'O', '0'),
                $('O', ' ', ' ', ' ', '0'),
                $('O', ' ', 'O', ' ', '0'),
                $('O', ' ', ' ', ' ', '0'),
                $('O', 'O', ' ', 'O', '0')
        );

        // when
        char[][] result = LabEscape.drawPathForEscape(labyrinth, startX, startY);

        // then
        assertThat(result).isEqualTo($$(
                $('O', 'O', 'O', 'O', '0'),
                $('O', ' ', '•', '•', '0'),
                $('O', ' ', 'O', '•', '0'),
                $('O', ' ', '•', '•', '0'),
                $('O', 'O', '•', 'O', '0')
        ));
    }

    @Test
    public void finds_out_there_is_no_path() {
        // given
        int startX = 1;
        int startY = 1;

        char[][] labyrinth = $$(
                $('O', 'O', 'O', 'O'),
                $('O', ' ', ' ', '0'),
                $('O', ' ', ' ', '0'),
                $('O', '0', '0', '0')
        );

        // then
        thrown.expect(NoEscapeException.class);

        // when
        LabEscape.drawPathForEscape(labyrinth, startX, startY);
    }

    private char[][] $$(char[]... rows) {
        return rows;
    }

    private char[] $(char... cells) {
        return cells;
    }
}