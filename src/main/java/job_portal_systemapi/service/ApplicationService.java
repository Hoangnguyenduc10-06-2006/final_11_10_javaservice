package job_portal_systemapi.service;

import job_portal_systemapi.model.dto.request.ApplyJobRequest;
import job_portal_systemapi.model.dto.request.UpdateApplicationStatusRequest;
import job_portal_systemapi.model.dto.response.ApplicationResponse;

import java.util.List;

public interface ApplicationService {

    ApplicationResponse applyJob(ApplyJobRequest request);

    List<ApplicationResponse> getApplicationsForEmployer();

    ApplicationResponse updateApplicationStatus(Long id, UpdateApplicationStatusRequest request);
}