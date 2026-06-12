package job_portal_systemapi.service;


public interface EmailService {
    void sendOtp(String email, String otp);
}
