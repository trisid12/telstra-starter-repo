package au.com.telstra.simcardactivator.repository;

import au.com.telstra.simcardactivator.model.SimActivationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimCardActivationRepo extends JpaRepository<SimActivationRequest, Long> {

}
