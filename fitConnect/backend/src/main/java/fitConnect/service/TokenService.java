package fitConnect.service;

import fitConnect.config.security.JwtProvider;
import fitConnect.dto.response.UserResponseDto;
import fitConnect.entity.user.User;
import fitConnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenService {
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    public String createNewAccessToken(String refreshToken){
        if(!jwtProvider.validateToken(refreshToken)){
            throw new IllegalArgumentException("unexpected token");
        }

        String userId=refreshTokenService.findByRefreshToken(refreshToken).getUserId();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new IllegalArgumentException("unexpected user"));
        UserResponseDto userResponseDto=new UserResponseDto(user);
        return jwtProvider.createToken(userResponseDto);
    }
}
