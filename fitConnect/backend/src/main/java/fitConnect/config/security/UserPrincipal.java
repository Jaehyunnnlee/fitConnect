package fitConnect.config.security;

import fitConnect.entity.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private Optional<User> user;

    public UserPrincipal(Optional<User> user){
        this.user=user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles=new ArrayList<>();
        roles.add("ROLE_"+user.get().getRole().toString());

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.get().getUserPwd();
    }

    @Override
    public String getUsername() {
        return user.get().getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
