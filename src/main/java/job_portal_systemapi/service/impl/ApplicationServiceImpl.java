package job_portal_systemapi.service.impl;

import job_portal_systemapi.enums.ApplicationStatusEnum;
import job_portal_systemapi.enums.JobStatusEnum;
import job_portal_systemapi.exception.AppliExistJob;
import job_portal_systemapi.exception.NotFoundJob;
import job_portal_systemapi.exception.NotFoundUser;
import job_portal_systemapi.exception.jobStatusException;
import job_portal_systemapi.model.dto.request.ApplyJobRequest;
import job_portal_systemapi.model.dto.request.UpdateApplicationStatusRequest;
import job_portal_systemapi.model.dto.response.ApplicationResponse;
import job_portal_systemapi.model.entity.Application;
import job_portal_systemapi.model.entity.JobPosting;
import job_portal_systemapi.model.entity.Users;
import job_portal_systemapi.repository.ApplicationRepository;
import job_portal_systemapi.repository.JobPostingRepository;
import job_portal_systemapi.repository.UserRepository;
import job_portal_systemapi.security.principal.CustomUserDetails;
import job_portal_systemapi.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;

    @Override
    public ApplicationResponse applyJob(ApplyJobRequest request) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Users candidate = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundUser("Không tìm thấy candidate"));

        JobPosting job = jobPostingRepository.findById(request.getJobId())
                .orElseThrow(() -> new NotFoundJob("Không tìm thấy tin tuyển dụng"));

        if (job.getStatus() != JobStatusEnum.APPROVED) {
            throw new jobStatusException("Tin tuyển dụng chưa được duyệt hoặc đã đóng");
        }

        if (applicationRepository.existsByCandidateAndJobPosting(candidate, job)) {
            throw new AppliExistJob("Bạn đã ứng tuyển công việc này rồi");
        }

        Application application = Application.builder()
                .candidate(candidate)
                .jobPosting(job)
                .coverLetter(request.getCoverLetter())
                .cvUrl(request.getCvUrl())
                .status(ApplicationStatusEnum.PENDING)
                .build();

        Application saved = applicationRepository.save(application);

        return ApplicationResponse.builder()
                .id(saved.getId())
                .jobId(job.getId())
                .jobTitle(job.getTitle())
                .candidateUsername(candidate.getUsername())
                .coverLetter(saved.getCoverLetter())
                .cvUrl(saved.getCvUrl())
                .status(saved.getStatus())
                .appliedAt(saved.getAppliedAt())
                .build();
    }

    @Override
    public List<ApplicationResponse> getApplicationsForEmployer() {

        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        Users employer = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundUser("Không tìm thấy employer"));

        return applicationRepository.findByJobPostingEmployer(employer)
                .stream()
                .map(app -> ApplicationResponse.builder()
                        .id(app.getId())
                        .jobId(app.getJobPosting().getId())
                        .jobTitle(app.getJobPosting().getTitle())
                        .candidateUsername(app.getCandidate().getUsername())
                        .coverLetter(app.getCoverLetter())
                        .cvUrl(app.getCvUrl())
                        .status(app.getStatus())
                        .appliedAt(app.getAppliedAt())
                        .build())
                .toList();
    }

    @Override
    public ApplicationResponse updateApplicationStatus(Long id, UpdateApplicationStatusRequest request) {

        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        Users employer = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundUser("Không tìm thấy employer"));

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hồ sơ ứng tuyển"));

        if (!application.getJobPosting().getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("Bạn không có quyền cập nhật hồ sơ này");
        }

        application.setStatus(request.getStatus());

        Application saved = applicationRepository.save(application);

        return ApplicationResponse.builder()
                .id(saved.getId())
                .jobId(saved.getJobPosting().getId())
                .jobTitle(saved.getJobPosting().getTitle())
                .candidateUsername(saved.getCandidate().getUsername())
                .coverLetter(saved.getCoverLetter())
                .cvUrl(saved.getCvUrl())
                .status(saved.getStatus())
                .appliedAt(saved.getAppliedAt())
                .build();
    }
}
