package myBlog.entity.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;


@Getter
public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private boolean isAccountNonLocked= true;
    private boolean isAccountNonExpired=true;
    private boolean isCredentialsNonExpired=true;
    private boolean isEnabled=true;
    private Collection<? extends GrantedAuthority> authorities;

    private String realName;
    private LocalDateTime createdAt;

    public CustomUserDetails(User user, String realName, LocalDateTime createdAt,Collection<?extends GrantedAuthority> authorities){
        this.username=user.getUserId();
        this.password=user.getUserPwd();
        this.realName=realName;
        this.createdAt=createdAt;
        this.authorities=authorities;
    }
}
