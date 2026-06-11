package job_portal_systemapi.service;


import job_portal_systemapi.model.dto.request.CreateJobRequest;
import job_portal_systemapi.model.dto.response.JobResponse;

import java.util.List;

public interface JobPostingService {
    //emplyee
    JobResponse createJob(CreateJobRequest request);
    List<JobResponse> getMyJobs();
    JobResponse getMyJobById(Long id);
    JobResponse updateMyJob(Long id, CreateJobRequest request);
    void deleteMyJob(Long id);

    //bên candidate
    List<JobResponse> getJobsForCandidate();
    JobResponse getJobForCandidateById(Long id);

    // bên admin
}