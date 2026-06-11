package job_portal_systemapi.repository;

import job_portal_systemapi.model.entity.RefreshToken;
import job_portal_systemapi.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    // tìm reftoken bằng tokem
    Optional<RefreshToken> findByToken(String token);
    // danh sach refreshtoken theo user
    List<RefreshToken> findByUser(Users user);
// xóa token cua người do
    void deleteByUser(Users user);
}
