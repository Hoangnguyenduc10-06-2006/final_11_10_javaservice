package job_portal_systemapi.service.impl;

import job_portal_systemapi.enums.RoleEnum;
import job_portal_systemapi.exception.*;
import job_portal_systemapi.model.dto.request.*;
import job_portal_systemapi.model.dto.response.JWTResponse;
import job_portal_systemapi.model.dto.response.UserResponse;
import job_portal_systemapi.model.entity.PasswordResetToken;
import job_portal_systemapi.model.entity.RefreshToken;
import job_portal_systemapi.model.entity.TokenBlacklist;
import job_portal_systemapi.model.entity.Users;
import job_portal_systemapi.repository.PasswordResetTokenRepository;
import job_portal_systemapi.repository.TokenBlacklistRepository;
import job_portal_systemapi.repository.UserRepository;
import job_portal_systemapi.security.jwt.JWTProvider;
import job_portal_systemapi.security.principal.CustomUserDetails;
import job_portal_systemapi.security.principal.CustomUserDetailsService;
import job_portal_systemapi.service.AuthService;
import job_portal_systemapi.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final TokenBlacklistRepository tokenBlacklistRepository;


    @Override
    public UserResponse registerUserCANDIDATE(RegisterRequest registerRequest) {

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new ConfirmPassNotTrue("Mật khẩu xác nhận không khớp");
        }

        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new UsernameExist("Username đã tồn tại");

        }

        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new EmailExist("Email đã tồn tại");
        }

        Users user = Users.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(RoleEnum.ROLE_CANDIDATE)
                .active(true)
                .build();

        Users newUser =userRepository.save(user);

        return UserResponse.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .role(newUser.getRole())
                .active(newUser.getActive())
                .build();
    }

    @Override
    public UserResponse registerUserEMPLOYER(RegisterRequest registerRequest) {

        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new ConfirmPassNotTrue("Mật khẩu xác nhận không khớp");
        }

        if(userRepository.existsByUsername(registerRequest.getUsername())){
            throw new UsernameExist("Username đã tồn tại");

        }

        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new EmailExist("Email đã tồn tại");
        }

        Users user = Users.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(RoleEnum.ROLE_EMPLOYER)
                .active(true)
                .build();

        Users newUser =userRepository.save(user);

        return UserResponse.builder()
                .id(newUser.getId())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .role(newUser.getRole())
                .active(newUser.getActive())
                .build();
    }

    @Override
    public JWTResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String token =jwtProvider.generateToken(userDetails.getUsername());
        String refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername()).getToken();
        return JWTResponse.builder()
                .username(userDetails.getUsername())
                .active(userDetails.isEnabled())
                .authorities(userDetails.getAuthorities())
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public JWTResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        Users user = refreshToken.getUser();

        String newAccessToken = jwtProvider.generateToken(user.getUsername());

        return JWTResponse.builder()
                .username(user.getUsername())
                .active(user.getActive())
                .token(newAccessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    public void logout(String accessToken, LogoutRequest request) {
        refreshTokenService.revokeRefreshToken(request.getRefreshToken());

        String token = accessToken.replace("Bearer ", "");

     Users users =userRepository.findByUsername(jwtProvider.getUsernameFromToken(token)).orElseThrow(()-> new NotFoundUser("Không tìm thấy user"));

        TokenBlacklist tokenBlacklist = TokenBlacklist.builder()
                .tokenString(token)
                .revokedAt(LocalDateTime.now())
                .users(users)
                .build();
        tokenBlacklistRepository.save(tokenBlacklist);
    }
    @Override
    public void changePassword(ChangePasswordRequest request) {

        CustomUserDetails userDetails =
                (CustomUserDetails) SecurityContextHolder
                        .getContext()
                        .getAuthentication()
                        .getPrincipal();

        Users user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundUser("Không tìm thấy user"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new NotTruePasoOld("Mật khẩu cũ không đúng");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ConfirmPassNotTrue("Mật khẩu xác nhận không khớp");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user);
    }

    @Override
    public String forgotPassword(ForgotPasswordRequest request) {

        Users user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new NotFoundUser("Email không tồn tại"));

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(token)
                .expiryDate(LocalDateTime.now().plusMinutes(15))
                .used(false)
                .user(user)
                .build();

        passwordResetTokenRepository.save(resetToken);

        return token;
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) {

        PasswordResetToken resetToken = passwordResetTokenRepository
                .findByToken(request.getToken())
                .orElseThrow(() -> new JWTValidate("Token reset password không tồn tại"));

        if (resetToken.getUsed()) {
            throw new JWTValidate("Token reset password đã được sử dụng");
        }

        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new JWTValidate("Token reset password đã hết hạn");
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new ConfirmPassNotTrue("Mật khẩu xác nhận không khớp");
        }

        Users user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        resetToken.setUsed(true);

        userRepository.save(user);
        passwordResetTokenRepository.save(resetToken);
    }
}
