package fitConnect.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final MyuserDetailService myuserDetailService;
    private final JwtProvider jwtProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autorizationHeader=request.getHeader("Authorization");
//        log.info("autorizationHeader: {}",autorizationHeader);
        if(autorizationHeader!=null&&autorizationHeader.startsWith("Bearer ")){
            String token=autorizationHeader.split(" ")[1];
//            log.info("token: {}",token);
            if(jwtProvider.validateToken(token)){
                String userId=jwtProvider.getUsername(token);
//                log.info("userId: {}",userId);
                UserDetails userDetails=myuserDetailService.loadUserByUsername(userId);
                if(userDetails!=null){
                    log.info("Role: {}",userDetails.getAuthorities());
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
