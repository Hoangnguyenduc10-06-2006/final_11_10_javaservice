package job_portal_systemapi.controller;

import job_portal_systemapi.model.dto.request.UpdateApplicationStatusRequest;
import job_portal_systemapi.model.dto.response.ApiDataResponse;
import job_portal_systemapi.model.dto.response.ApplicationResponse;
import job_portal_systemapi.service.ApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employer/applications")
@RequiredArgsConstructor
public class EmployerApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<ApiDataResponse<List<ApplicationResponse>>> getApplications() {
        return ResponseEntity.ok(new ApiDataResponse<>(
                true,
                "Lấy danh sách hồ sơ ứng tuyển thành công",
                applicationService.getApplicationsForEmployer(),
                null,
                HttpStatus.OK
        ));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiDataResponse<ApplicationResponse>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateApplicationStatusRequest request
    ) {
        return ResponseEntity.ok(new ApiDataResponse<>(
                true,
                "Cập nhật trạng thái hồ sơ thành công",
                applicationService.updateApplicationStatus(id, request),
                null,
                HttpStatus.OK
        ));
    }
}
