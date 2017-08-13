package co.tide.labescape;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LabEscapeAcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Test
    public void finds_escape_path() throws Exception {
        // given
        Map<String, Integer> requestParams = ImmutableMap.of(
                "x", 3,
                "y", 1
        );

        String labyrinth =
                "OOOOOOOOOO\n" +
                "O    O   O\n" +
                "O OO O O O\n" +
                "O  O O O O\n" +
                "O OO   O  \n" +
                "O OOOOOOOO\n" +
                "O        O\n" +
                "OOOOOOOOOO";

        // when
        ResponseEntity<String> response = restTemplate.postForEntity(appUrl(), labyrinth, String.class, requestParams);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo(
                "OOOOOOOOOO\n" +
                "O••••O•••O\n" +
                "O•OO•O•O•O\n" +
                "O• O•O•O•O\n" +
                "O OO•••O••\n" +
                "O OOOOOOOO\n" +
                "O        O\n" +
                "OOOOOOOOOO");
    }

    @Test
    public void finds_out_if_there_is_no_escape_path() throws Exception {
        // given
        Map<String, Integer> requestParams = ImmutableMap.of(
                "x", 1,
                "y", 1
        );

        String labyrinth =
                "OOOOOO\n" +
                "O    O\n" +
                "O    O\n" +
                "OOOOOO";

        // when
        ResponseEntity<String> response = restTemplate.postForEntity(appUrl(), labyrinth, String.class, requestParams);

        // then
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody()).isEqualTo("No escape");
    }


    private String appUrl() {
        return "http://localhost:" + port + "/lab-escape?startX={x}&startY={y}";
    }

}
