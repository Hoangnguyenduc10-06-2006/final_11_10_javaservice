package job_portal_systemapi.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangePasswordRequest {

    @NotBlank(message = "mẫu khẩu cũ không được để trống")
    private String oldPassword;

    @NotBlank(message = "mẫu khẩu mới không được để trống")
    private String newPassword;

    @NotBlank(message = "xác nhận mẫu khẩu không được để trống")
    private String confirmPassword;
}
