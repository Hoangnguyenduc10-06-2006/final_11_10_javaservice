package job_portal_systemapi.model.dto.request;


import jakarta.validation.constraints.NotNull;
import job_portal_systemapi.enums.ApplicationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateApplicationStatusRequest {

    @NotNull(message = "Trạng thái không được để trống")
    private ApplicationStatusEnum status;
}
