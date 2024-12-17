package fitConnect.restController;


import fitConnect.dto.AccessTokenRequestDto;
import fitConnect.dto.response.AccessTokenResponseDto;
import fitConnect.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest-api/token")
public class TokenRestController {
    private final TokenService tokenService;
    @PostMapping
    public ResponseEntity<AccessTokenResponseDto> createNewAccessToken(@RequestBody AccessTokenRequestDto requestDto){
        String newAccessToken=tokenService.createNewAccessToken(requestDto.getRefreshToken());
        return ResponseEntity.status(HttpStatus.CREATED).body(new AccessTokenResponseDto(newAccessToken));
    }
}
