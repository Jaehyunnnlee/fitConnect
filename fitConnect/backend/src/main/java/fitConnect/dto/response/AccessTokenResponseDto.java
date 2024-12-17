package fitConnect.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AccessTokenResponseDto {
    private String accessToken;

    public AccessTokenResponseDto(String accessToken){
        this.accessToken=accessToken;
    }
}
