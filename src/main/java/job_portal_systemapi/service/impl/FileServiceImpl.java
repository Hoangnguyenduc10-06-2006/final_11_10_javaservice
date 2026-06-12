package job_portal_systemapi.service.impl;

import com.cloudinary.Cloudinary;
import job_portal_systemapi.exception.failUploadCV;
import job_portal_systemapi.model.dto.response.UploadCVResponse;
import job_portal_systemapi.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final Cloudinary cloudinary;

    @Override
    public UploadCVResponse uploadCV(MultipartFile file) {

        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), Map.of("resource_type", "auto"));

            return UploadCVResponse.builder()
                    .fileName(file.getOriginalFilename())
                    .cvUrl(uploadResult.get("secure_url").toString())
                    .build();

        } catch (Exception e) {
            throw new failUploadCV("Upload CV thất bại"+e.getMessage());
        }
    }
}