package fitConnect.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import fitConnect.entity.user.Role;
import fitConnect.entity.user.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String userId;
    private String userName;
    private LocalDateTime createdAt;
    private Role role;

    public UserResponseDto(User entity){
        this.userId= entity.getUserId();
        this.userName=entity.getUserName();
        this.createdAt=entity.getCreateAt();
        this.role=entity.getRole();
    }
}
