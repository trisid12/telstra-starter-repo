package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.model.SimActivationRequest;
import au.com.telstra.simcardactivator.repository.SimCardActivationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sim")
public class SimActivationController {

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private SimCardActivationRepo repo;

    @PostMapping("/activate")
    public ResponseEntity<String> activatesim(@RequestBody SimActivationRequest request) {

        String actuatorUrl = "http://localhost:8444/actuate";

        Map<String, String> payload = Map.of("iccid", request.getIccid());

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(actuatorUrl, payload, Map.class);
            boolean success = (Boolean) response.getBody().get("success");

            //save activation result
            SimActivationRequest record = new SimActivationRequest(
                    request.getIccid(),
                    request.getCustomerEmail(),
                    success
            );
            repo.save(record);

            return ResponseEntity.ok("Activation " + (success ? "successful" : "failed"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Activation error");
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getSimCardStatus(@RequestParam Long simCardId){
        Optional<SimActivationRequest> result = repo.findById(simCardId);
        if(result.isPresent()){
            SimActivationRequest sim = result.get();
            Map<String,Object> response = Map.of(
                    "iccid", sim.getIccid(),
                    "customerEmail", sim.getCustomerEmail(),
                    "active", sim.isActive()
            );
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(404).body("SIM card not found");
        }
    }

}
