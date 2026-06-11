package job_portal_systemapi.controller;

import job_portal_systemapi.model.dto.request.CreateJobRequest;
import job_portal_systemapi.model.dto.response.ApiDataResponse;
import job_portal_systemapi.model.dto.response.JobResponse;
import job_portal_systemapi.service.JobPostingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employer/jobs")
@RequiredArgsConstructor
public class JobPostingController {

    private final JobPostingService jobPostingService;

    @PostMapping
    public ResponseEntity<ApiDataResponse<JobResponse>> createJob(
            @Valid @RequestBody CreateJobRequest request
    ) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Đăng tin tuyển dụng thành công",
                jobPostingService.createJob(request),
                null,
                HttpStatus.CREATED
        ), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<JobResponse>>> getMyJobs() {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy danh sách tin tuyển dụng thành công",
                jobPostingService.getMyJobs(),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ApiDataResponse<JobResponse>> getMyJobById(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Lấy chi tiết tin tuyển dụng thành công",
                jobPostingService.getMyJobById(id),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ApiDataResponse<JobResponse>> updateMyJob(
            @PathVariable Long id,
            @Valid @RequestBody CreateJobRequest request
    ) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Cập nhật tin tuyển dụng thành công",
                jobPostingService.updateMyJob(id, request),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiDataResponse<String>> deleteMyJob(
            @PathVariable Long id
    ) {

        jobPostingService.deleteMyJob(id);

        return ResponseEntity.ok(
                new ApiDataResponse<>(
                        true,
                        "Xóa tin tuyển dụng thành công",
                        null,
                        null,
                        HttpStatus.OK
                )
        );
    }
}
