package job_portal_systemapi.controller;


import job_portal_systemapi.model.dto.request.*;
import job_portal_systemapi.model.dto.response.ApiDataResponse;
import job_portal_systemapi.model.dto.response.JWTResponse;
import job_portal_systemapi.model.dto.response.UserResponse;
import job_portal_systemapi.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register-candidate")
    public ResponseEntity<ApiDataResponse<UserResponse>> registerCandidate(@Valid @RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Đăng ký thành công",
                authService.registerUserCANDIDATE(registerRequest),
                null,
                HttpStatus.CREATED
        ),HttpStatus.CREATED);
    }

    @PostMapping("/register-employer")
    public ResponseEntity<ApiDataResponse<UserResponse>> registerEmployer(@Valid @RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Đăng ký thành công",
                authService.registerUserEMPLOYER(registerRequest),
                null,
                HttpStatus.CREATED
        ),HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiDataResponse<JWTResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Đăng nhập thành công",
                authService.login(loginRequest),
                null,
                HttpStatus.OK
        ),HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiDataResponse<JWTResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request
    ) {
        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Refresh token thành công",
                authService.refreshToken(request),
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiDataResponse<String>> logout(
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody LogoutRequest request
    ) {
        authService.logout(authorization, request);

        return new ResponseEntity<>(new ApiDataResponse<>(
                true,
                "Đăng xuất thành công",
                null,
                null,
                HttpStatus.OK
        ), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<ApiDataResponse<String>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        authService.changePassword(request);

        return ResponseEntity.ok(new ApiDataResponse<>(
                true,
                "Đổi mật khẩu thành công",
                null,
                null,
                HttpStatus.OK
        ));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiDataResponse<String>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request
    ) {
        String token = authService.forgotPassword(request);

        return ResponseEntity.ok(new ApiDataResponse<>(
                true,
                "Tạo token đặt lại mật khẩu thành công",
                token,
                null,
                HttpStatus.OK
        ));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiDataResponse<String>> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request
    ) {
        authService.resetPassword(request);

        return ResponseEntity.ok(new ApiDataResponse<>(
                true,
                "Đặt lại mật khẩu thành công",
                null,
                null,
                HttpStatus.OK
        ));
    }
}
