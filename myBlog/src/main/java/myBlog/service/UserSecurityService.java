package myBlog.service;

import lombok.RequiredArgsConstructor;
import myBlog.entity.user.CustomUserDetails;
import myBlog.entity.user.Role;
import myBlog.entity.user.User;
import myBlog.repository.UserRepository;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> loginUser= userRepository.findByUserId(username);
        if(loginUser.isEmpty()){
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }
        User user=loginUser.get();
        String realName=user.getUserName();
        LocalDateTime createdAt=user.getCreateAt();
        List<GrantedAuthority> authorities=new ArrayList<>();
        if("admin".equals(username)){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        else{
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return new CustomUserDetails(user,realName,createdAt,authorities);
    }
}
