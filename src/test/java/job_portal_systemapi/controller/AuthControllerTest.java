package job_portal_systemapi.controller;


import job_portal_systemapi.enums.RoleEnum;
import job_portal_systemapi.model.dto.request.LoginRequest;
import job_portal_systemapi.model.dto.request.RegisterRequest;
import job_portal_systemapi.model.dto.response.JWTResponse;
import job_portal_systemapi.model.dto.response.UserResponse;
import job_portal_systemapi.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private tools.jackson.databind.ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @Test
    void registerCandidate_returnCreated() throws Exception {

        RegisterRequest request = RegisterRequest.builder()
                .username("candidate")
                .email("candidate@gmail.com")
                .password("123456")
                .confirmPassword("123456")
                .build();

        UserResponse response = UserResponse.builder()
                .id(1L)
                .username("candidate")
                .email("candidate@gmail.com")
                .role(RoleEnum.ROLE_CANDIDATE)
                .active(true)
                .build();

        when(authService.registerUserCANDIDATE(any(RegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/register-candidate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void registerEmployer_returnCreated() throws Exception {

        RegisterRequest request = RegisterRequest.builder()
                .username("employer")
                .email("employer@gmail.com")
                .password("123456")
                .confirmPassword("123456")
                .build();

        UserResponse response = UserResponse.builder()
                .id(2L)
                .username("employer")
                .email("employer@gmail.com")
                .role(RoleEnum.ROLE_EMPLOYER)
                .active(true)
                .build();

        when(authService.registerUserEMPLOYER(any(RegisterRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/register-employer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void login_returnOk() throws Exception {

        LoginRequest request = LoginRequest.builder()
                .username("candidate")
                .password("123456")
                .build();

        JWTResponse response = JWTResponse.builder()
                .username("candidate")
                .active(true)
                .authorities(List.of(
                        new SimpleGrantedAuthority("ROLE_CANDIDATE")
                ))
                .token("fake-access-token")
                .refreshToken("fake-refresh-token")
                .build();

        when(authService.login(any(LoginRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}