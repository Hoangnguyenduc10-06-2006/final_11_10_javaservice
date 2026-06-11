package job_portal_systemapi.service;

import job_portal_systemapi.model.dto.request.LoginRequest;
import job_portal_systemapi.model.dto.request.LogoutRequest;
import job_portal_systemapi.model.dto.request.RefreshTokenRequest;
import job_portal_systemapi.model.dto.request.RegisterRequest;
import job_portal_systemapi.model.dto.response.JWTResponse;
import job_portal_systemapi.model.dto.response.UserResponse;


public interface AuthService {
    UserResponse registerUserCANDIDATE(RegisterRequest registerRequest);

    UserResponse registerUserEMPLOYER(RegisterRequest registerRequest);

    JWTResponse login(LoginRequest loginRequest);

    JWTResponse refreshToken(RefreshTokenRequest request);

    void logout(String accessToken, LogoutRequest request);
}
