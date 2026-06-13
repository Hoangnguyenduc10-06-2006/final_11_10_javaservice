package job_portal_systemapi.service;

import job_portal_systemapi.enums.RoleEnum;
import job_portal_systemapi.exception.UsernameExist;
import job_portal_systemapi.model.dto.request.LoginRequest;
import job_portal_systemapi.model.dto.request.RegisterRequest;
import job_portal_systemapi.model.dto.response.JWTResponse;
import job_portal_systemapi.model.dto.response.UserResponse;
import job_portal_systemapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;


    @Test
    void registerUserCandidate_success() {
        RegisterRequest request = RegisterRequest.builder()
                .username("candidate01")
                .email("candidate01@gmail.com")
                .password("123456")
                .confirmPassword("123456")
                .build();

        UserResponse response = authService.registerUserCANDIDATE(request);

        assertEquals("candidate01", response.getUsername());
        assertEquals(RoleEnum.ROLE_CANDIDATE, response.getRole());
    }
    @Test
    void registerUserEmployer_success() {
        RegisterRequest request = RegisterRequest.builder()
                .username("employer01")
                .email("employer01@gmail.com")
                .password("123456")
                .confirmPassword("123456")
                .build();

        UserResponse response = authService.registerUserEMPLOYER(request);

        assertEquals("employer01", response.getUsername());
        assertEquals(RoleEnum.ROLE_EMPLOYER, response.getRole());
    }

    @Test
    void registerUser_whenUsernameExists_throwException() {
        RegisterRequest request1 = RegisterRequest.builder()
                .username("duplicate")
                .email("user1@gmail.com")
                .password("123456")
                .confirmPassword("123456")
                .build();

        authService.registerUserCANDIDATE(request1);

        RegisterRequest request2 = RegisterRequest.builder()
                .username("duplicate")
                .email("user2@gmail.com")
                .password("123456")
                .confirmPassword("123456")
                .build();

        assertThrows(UsernameExist.class, () -> authService.registerUserCANDIDATE(request2)
        );
    }
    @Test
    void login_success() {

        RegisterRequest request = RegisterRequest.builder()
                .username("loginuser")
                .email("login@gmail.com")
                .password("123456")
                .confirmPassword("123456")
                .build();

        authService.registerUserCANDIDATE(request);

        LoginRequest loginRequest = LoginRequest.builder()
                .username("loginuser")
                .password("123456")
                .build();

        JWTResponse response = authService.login(loginRequest);

        assertNotNull(response);
        assertNotNull(response.getToken());
        assertEquals("loginuser", response.getUsername());
    }

    @Test
    void login_wrongPassword() {

        RegisterRequest request = RegisterRequest.builder()
                .username("user01")
                .email("user01@gmail.com")
                .password("123456")
                .confirmPassword("123456")
                .build();

        authService.registerUserCANDIDATE(request);

        LoginRequest loginRequest = LoginRequest.builder()
                .username("user01")
                .password("111111")
                .build();

        assertThrows(
                Exception.class,
                () -> authService.login(loginRequest)
        );
    }
}
