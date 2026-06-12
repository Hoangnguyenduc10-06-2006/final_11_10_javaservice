package job_portal_systemapi.service;

import job_portal_systemapi.model.dto.request.*;
import job_portal_systemapi.model.dto.response.JWTResponse;
import job_portal_systemapi.model.dto.response.UserResponse;


public interface AuthService {
    UserResponse registerUserCANDIDATE(RegisterRequest registerRequest);

    UserResponse registerUserEMPLOYER(RegisterRequest registerRequest);

    JWTResponse login(LoginRequest loginRequest);

    JWTResponse refreshToken(RefreshTokenRequest request);

    void logout(String accessToken, LogoutRequest request);

    void changePassword(ChangePasswordRequest request);

    String forgotPassword(ForgotPasswordRequest request);

    void resetPassword(ResetPasswordRequest request);
}
