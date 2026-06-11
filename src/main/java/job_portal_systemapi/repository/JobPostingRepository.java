package job_portal_systemapi.repository;

import job_portal_systemapi.model.entity.JobPosting;
import job_portal_systemapi.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long> {
    List<JobPosting> findByEmployer(Users employer);
}