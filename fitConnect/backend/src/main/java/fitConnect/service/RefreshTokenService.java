package fitConnect.service;

import fitConnect.entity.RefreshToken;
import fitConnect.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken){
        return refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(()->new IllegalArgumentException("토큰 없음"));
    }
}
