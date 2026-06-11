package job_portal_systemapi.service;

import job_portal_systemapi.model.entity.RefreshToken;


public interface RefreshTokenService {

    RefreshToken createRefreshToken(String username);

    RefreshToken verifyRefreshToken(String token);

    void revokeRefreshToken(String token);
}