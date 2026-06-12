package job_portal_systemapi.service;

import job_portal_systemapi.model.dto.response.UploadCVResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    UploadCVResponse uploadCV(MultipartFile file);
}
