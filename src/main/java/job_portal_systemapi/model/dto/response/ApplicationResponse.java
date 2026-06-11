package job_portal_systemapi.model.dto.response;

import job_portal_systemapi.enums.ApplicationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private String candidateUsername;
    private String coverLetter;
    private String cvUrl;
    private ApplicationStatusEnum status;
    private LocalDateTime appliedAt;
}
