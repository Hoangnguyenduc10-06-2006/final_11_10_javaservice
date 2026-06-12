package job_portal_systemapi.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordOtpRequest {
    @Email
    @NotBlank(message = "Email không được để trống")
    private String email;
}
