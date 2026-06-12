package job_portal_systemapi.controller;

import job_portal_systemapi.model.dto.response.ApiDataResponse;
import job_portal_systemapi.model.dto.response.JobResponse;
import job_portal_systemapi.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/jobs")
@RequiredArgsConstructor
public class AdminJobController {

    private final JobPostingService jobPostingService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<JobResponse>>> getAllJobs() {
        return ResponseEntity.ok(new ApiDataResponse<>(
                true,
                "Lấy danh sách tin tuyển dụng thành công",
                jobPostingService.getAllJobsForAdmin(),
                null,
                HttpStatus.OK
        ));
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<ApiDataResponse<JobResponse>> approveJob(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(new ApiDataResponse<>(
                true,
                "Duyệt tin tuyển dụng thành công",
                jobPostingService.approveJob(id),
                null,
                HttpStatus.OK
        ));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<ApiDataResponse<JobResponse>> rejectJob(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(new ApiDataResponse<>(
                true,
                "Từ chối tin tuyển dụng thành công",
                jobPostingService.rejectJob(id),
                null,
                HttpStatus.OK
        ));
    }
}
