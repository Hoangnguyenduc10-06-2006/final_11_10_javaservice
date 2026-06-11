package job_portal_systemapi.model.dto.response;

import job_portal_systemapi.enums.JobStatusEnum;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private String salaryRange;
    private JobStatusEnum status;
    private String employerUsername;
}
