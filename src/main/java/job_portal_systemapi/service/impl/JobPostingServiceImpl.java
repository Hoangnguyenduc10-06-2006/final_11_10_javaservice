package job_portal_systemapi.service.impl;

import job_portal_systemapi.enums.JobStatusEnum;
import job_portal_systemapi.exception.NotFoundJob;
import job_portal_systemapi.exception.NotFoundUser;
import job_portal_systemapi.model.dto.request.CreateJobRequest;
import job_portal_systemapi.model.dto.response.JobResponse;
import job_portal_systemapi.model.entity.JobPosting;
import job_portal_systemapi.model.entity.Users;
import job_portal_systemapi.repository.JobPostingRepository;
import job_portal_systemapi.repository.UserRepository;
import job_portal_systemapi.security.principal.CustomUserDetails;
import job_portal_systemapi.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobPostingServiceImpl implements JobPostingService {
    private final JobPostingRepository jobPostingRepository;
    private final UserRepository userRepository;

    @Override
    public JobResponse createJob(CreateJobRequest request) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Users employer = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new NotFoundUser("Không tìm thấy employer"));

        JobPosting jobPosting = JobPosting.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .salaryRange(request.getSalaryRange())
                .employer(employer)
                .status(JobStatusEnum.PENDING_APPROVAL)
                .build();


        JobPosting jobSave = jobPostingRepository.save(jobPosting);
        return JobResponse.builder()
                .id(jobSave.getId())
                .title(jobSave.getTitle())
                .description(jobSave.getDescription())
                .salaryRange(jobSave.getSalaryRange())
                .status(jobSave.getStatus())
                .employerUsername(employer.getUsername())
                .build();
    }

    @Override
    public List<JobResponse> getMyJobs() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Users employer = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundUser("Không tìm thấy employer"));

        return jobPostingRepository.findByEmployer(employer).stream().map(
                        job -> JobResponse.builder()
                                .id(job.getId())
                                .title(job.getTitle())
                                .description(job.getDescription())
                                .salaryRange(job.getSalaryRange())
                                .status(job.getStatus())
                                .employerUsername(job.getEmployer().getUsername())
                                .build()
                )
                .toList();
    }

    @Override
    public JobResponse getMyJobById(Long id) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Users employer = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundUser("Không tìm thấy employer"));

        JobPosting job = jobPostingRepository.findById(id)
                .orElseThrow(() -> new NotFoundJob("Không tìm thấy tin tuyển dụng"));

        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("Bạn không có quyền xem tin này");
        }

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .salaryRange(job.getSalaryRange())
                .status(job.getStatus())
                .employerUsername(job.getEmployer().getUsername())
                .build();
    }

    @Override
    public JobResponse updateMyJob(Long id, CreateJobRequest request) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        Users employer = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundUser("Không tìm thấy employer"));

        JobPosting job = jobPostingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy tin tuyển dụng"));

        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("Bạn không có quyền sửa tin này");
        }

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setSalaryRange(request.getSalaryRange());

        JobPosting savedJob = jobPostingRepository.save(job);

        return JobResponse.builder()
                .id(savedJob.getId())
                .title(savedJob.getTitle())
                .description(savedJob.getDescription())
                .salaryRange(savedJob.getSalaryRange())
                .status(savedJob.getStatus())
                .employerUsername(savedJob.getEmployer().getUsername())
                .build();
    }

    @Override
    public void deleteMyJob(Long id) {

        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        Users employer = userRepository.findByUsername(
                        userDetails.getUsername())
                .orElseThrow(() ->
                        new NotFoundUser("Không tìm thấy employer"));

        JobPosting job = jobPostingRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundJob("Không tìm thấy tin tuyển dụng"));

        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException(
                    "Bạn không có quyền xóa tin này");
        }

        job.setStatus(JobStatusEnum.CLOSED);

        jobPostingRepository.save(job);
    }

    @Override
    public List<JobResponse> getJobsForCandidate() {
        return jobPostingRepository.findAll()
                .stream()
                .filter(job -> job.getStatus() == JobStatusEnum.APPROVED)
                .map(job -> JobResponse.builder()
                        .id(job.getId())
                        .title(job.getTitle())
                        .description(job.getDescription())
                        .salaryRange(job.getSalaryRange())
                        .status(job.getStatus())
                        .employerUsername(job.getEmployer().getUsername())
                        .build())
                .toList();
    }
    @Override
    public JobResponse getJobForCandidateById(Long id) {

        JobPosting job = jobPostingRepository.findById(id)
                .orElseThrow(() -> new NotFoundJob("Không tìm thấy tin tuyển dụng"));

        if (job.getStatus() != JobStatusEnum.APPROVED) {
            throw new NotFoundJob("Tin tuyển dụng không khả dụng");
        }

        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .salaryRange(job.getSalaryRange())
                .status(job.getStatus())
                .employerUsername(job.getEmployer().getUsername())
                .build();
    }


}
