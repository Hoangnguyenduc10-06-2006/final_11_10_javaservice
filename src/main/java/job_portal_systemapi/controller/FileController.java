package job_portal_systemapi.controller;
import job_portal_systemapi.model.dto.response.ApiDataResponse;
import job_portal_systemapi.model.dto.response.UploadCVResponse;
import job_portal_systemapi.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/candidate")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload-cv")
    public ResponseEntity<ApiDataResponse<UploadCVResponse>> uploadCV(
            @RequestParam("file") MultipartFile file
    ) {

        return new ResponseEntity<>(
                new ApiDataResponse<>(
                        true,
                        "Upload CV thành công",
                        fileService.uploadCV(file),
                        null,
                        HttpStatus.OK
                ),
                HttpStatus.OK
        );
    }
}
