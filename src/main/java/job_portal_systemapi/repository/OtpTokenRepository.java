package job_portal_systemapi.repository;

import job_portal_systemapi.model.entity.OtpToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpTokenRepository extends JpaRepository<OtpToken, Long> {

    Optional<OtpToken> findTopByEmailAndOtpAndUsedFalseOrderByIdDesc(
            String email,
            String otp
    );
}