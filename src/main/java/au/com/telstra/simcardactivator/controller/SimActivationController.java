package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.model.SimActivationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/api/sim")
public class SimActivationController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/activate")
    public ResponseEntity<String> activatesim(@RequestBody SimActivationRequest request) {

        String actuatorUrl = "http://localhost:8444/actuate";

        Map<String, String> payload = Map.of("iccid", request.getIccid());

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(actuatorUrl, payload, Map.class);
            boolean success = (Boolean) response.getBody().get("success");

            System.out.println("Activation " + (success ? "successful" : "failed") + " for ICCID: " + request.getIccid());
            return ResponseEntity.ok("Activation " + (success ? "successful" : "failed"));

        } catch (Exception e) {
            System.out.println("Error during activation: " + e.getMessage());
            return ResponseEntity.status(500).body("Activation error");
        }
    }

}
