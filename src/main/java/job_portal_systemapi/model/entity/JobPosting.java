package job_portal_systemapi.model.entity;

import job_portal_systemapi.enums.JobStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "job_postings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// tin tuyeern dụng
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String salaryRange;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatusEnum status;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Users employer;

    @OneToMany(mappedBy = "jobPosting", cascade = CascadeType.ALL)
    private List<Application> applications;
}