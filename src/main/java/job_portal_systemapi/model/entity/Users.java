package job_portal_systemapi.model.entity;

import job_portal_systemapi.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RoleEnum role;

    @Column(nullable = false)
    private Boolean active = true;
//
//    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
//    private List<JobPosting> jobPostings;
//
//    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
//    private List<Application> applications;
//
//    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
//    private List<TokenBlacklist> tokenBlacklists;
}