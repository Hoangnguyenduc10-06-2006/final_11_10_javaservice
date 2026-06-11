package job_portal_systemapi.service.impl;

import job_portal_systemapi.exception.JWTValidate;
import job_portal_systemapi.exception.NotFoundUser;
import job_portal_systemapi.model.entity.RefreshToken;
import job_portal_systemapi.model.entity.Users;

import job_portal_systemapi.repository.RefreshTokenRepository;
import job_portal_systemapi.repository.UserRepository;
import job_portal_systemapi.security.jwt.JWTProvider;
import job_portal_systemapi.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JWTProvider jwtProvider;

    @Value("${jwt-refresh-expire}")
    private Long refreshTokenExpire;

    @Override
    public RefreshToken createRefreshToken(String username) {

        Users user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundUser("Không tìm thấy user"));

        RefreshToken refreshToken = RefreshToken.builder()
                .token(jwtProvider.generateRefreshToken())
                .expiryDate(LocalDateTime.now().plusSeconds(refreshTokenExpire / 1000))
                .revoked(false)
                .user(user)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new JWTValidate("Refresh token không tồn tại"));

        if (refreshToken.getRevoked()) {
            throw new JWTValidate("Refresh token đã bị thu hồi");
        }

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new JWTValidate("Refresh token đã hết hạn");
        }
        return refreshToken;
    }

    @Override
    public void revokeRefreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new JWTValidate("Refresh token không tồn tại"));

        refreshToken.setRevoked(true);

        refreshTokenRepository.save(refreshToken);
    }

}