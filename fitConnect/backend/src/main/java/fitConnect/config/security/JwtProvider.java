package fitConnect.config.security;

import fitConnect.controller.dto.response.UserResponseDto;
import fitConnect.entity.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class JwtProvider {
    private final Key key;
    private final long accessTokenExpTime;

    public JwtProvider(
            @Value("${jwt.secret}") String secretKey,@Value("${jwt.expiration}") long expiration){
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
        this.key= Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpTime = expiration;
    }

    public String createToken(UserResponseDto user) {
        return generateToken(user, accessTokenExpTime);
    }
    // JWT 생성
    public String generateToken(UserResponseDto user, long expiration) {
       Claims claims=Jwts.claims();
       claims.put("userId",user.getUserId());
       claims.put("userName",user.getUserName());
       claims.put("role",user.getRole());

        ZonedDateTime now=ZonedDateTime.now();
        ZonedDateTime tokenValidity=now.plusSeconds(expiration);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.toInstant()))
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
    }

    // JWT에서 사용자 이름 추출
    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("userId", String.class);
    }

    // JWT 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
        }
        return false;
    }
}
