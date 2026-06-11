package job_portal_systemapi.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "token_blacklist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenBlacklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String tokenString;

    @Column(nullable = false)
    private LocalDateTime revokedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;
}
