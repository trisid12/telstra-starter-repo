package stepDefinitions;

import io.cucumber.java.en.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SimCardActivatorStepDefinitions {

    private final RestTemplate restTemplate = new RestTemplate();
    private String iccid;
    private String email;
    private ResponseEntity<String> activationResponse;

    @Given("the SIM card ICCID is {string} and email is {string}")
    public void the_sim_card_iccid_is_and_email_is(String iccid, String email) {
        this.iccid = iccid;
        this.email = email;
    }

    @When("I activate the SIM card")
    public void i_activate_the_sim_card() {
        String url = "http://localhost:8080/api/sim/activate";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String body = String.format("{\"iccid\": \"%s\", \"customerEmail\": \"%s\"}", iccid, email);
        HttpEntity<String> request = new HttpEntity<>(body, headers);

        activationResponse = restTemplate.postForEntity(url, request, String.class);
    }

    @Then("the SIM card with ID {int} should be active")
    public void the_sim_card_with_id_should_be_active(Integer id) {
        String url = "http://localhost:8080/api/sim/status?simCardId=" + id;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue((Boolean) response.getBody().get("active"));
    }

    @Then("the SIM card with ID {int} should not be active")
    public void the_sim_card_with_id_should_not_be_active(Integer id) {
        String url = "http://localhost:8080/api/sim/status?simCardId=" + id;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        assertEquals(200, response.getStatusCodeValue());
        assertFalse((Boolean) response.getBody().get("active"));
    }
}
