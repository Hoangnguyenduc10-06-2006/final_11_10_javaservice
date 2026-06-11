package job_portal_systemapi.model.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplyJobRequest {

    @NotNull(message = "Job ID không được để trống")
    private Long jobId;

    private String coverLetter;

    private String cvUrl;
}
