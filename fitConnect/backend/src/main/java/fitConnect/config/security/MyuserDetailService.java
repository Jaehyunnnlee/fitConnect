package fitConnect.config.security;

import fitConnect.entity.user.User;
import fitConnect.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyuserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyuserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user=userRepository.findByUserId(username);
        if(user==null){
            throw new UsernameNotFoundException("유저가 존재하지 않습니다.");
        }
        return new UserPrincipal(user);
    }
}
