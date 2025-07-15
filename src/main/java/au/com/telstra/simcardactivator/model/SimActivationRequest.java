package au.com.telstra.simcardactivator.model;

public class SimActivationRequest {
    
    private final String iccid;
    private final String customerEmail;

    public SimActivationRequest(String iccid, String customerEmail) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
    }

    public String getIccid() {
        return iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
}
