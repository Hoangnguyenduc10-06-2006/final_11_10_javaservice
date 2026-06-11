package job_portal_systemapi.controller;

import jakarta.validation.Valid;
import job_portal_systemapi.model.dto.request.ApplyJobRequest;
import job_portal_systemapi.model.dto.response.ApiDataResponse;
import job_portal_systemapi.model.dto.response.ApplicationResponse;
import job_portal_systemapi.model.dto.response.JobResponse;
import job_portal_systemapi.service.ApplicationService;
import job_portal_systemapi.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidate/jobs")
@RequiredArgsConstructor
public class CandidateController {

    private final JobPostingService jobPostingService;
    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<JobResponse>>> getJobsForCandidate() {
        return ResponseEntity.ok(new ApiDataResponse<>(
                true,
                "Lấy danh sách việc làm thành công",
                jobPostingService.getJobsForCandidate(),
                null,
                HttpStatus.OK
        ));
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<JobResponse>> getJobById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(new ApiDataResponse<>(
                true,
                "Lấy chi tiết việc làm thành công",
                jobPostingService.getJobForCandidateById(id),
                null,
                HttpStatus.OK
        ));
    }

    @PostMapping
    public ResponseEntity<ApiDataResponse<ApplicationResponse>> applyJob(
            @Valid @RequestBody ApplyJobRequest request
    ) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Ứng tuyển thành công",
                applicationService.applyJob(request),
                null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }
}
