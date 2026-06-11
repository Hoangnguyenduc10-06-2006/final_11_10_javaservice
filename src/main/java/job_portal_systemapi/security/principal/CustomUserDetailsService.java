package job_portal_systemapi.security.principal;

import job_portal_systemapi.model.entity.Users;
import job_portal_systemapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("không tìm thấy username: " + username));
        return CustomUserDetails.builder()
                .username(users.getUsername())
                .password(users.getPassword())
                .email(users.getEmail())
                .active(users.getActive())
                .authorities(List.of(new SimpleGrantedAuthority(users.getRole().name())))
                .build();
    }

}
