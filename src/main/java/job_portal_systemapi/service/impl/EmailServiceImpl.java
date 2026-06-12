package job_portal_systemapi.service.impl;

import job_portal_systemapi.service.EmailService;
import  lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendOtp(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);

        message.setSubject("job_portal_system - OTP Reset Password");

        message.setText("Mã OTP của bạn là: " + otp + "\nOTP có hiệu lực trong 5 phút.");

        mailSender.send(message);
    }
}