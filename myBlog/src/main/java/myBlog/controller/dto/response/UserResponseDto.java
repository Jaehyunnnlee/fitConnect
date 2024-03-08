package myBlog.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import myBlog.entity.user.Role;
import myBlog.entity.user.User;

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
        this.userName=entity.getUserId();
        this.createdAt=entity.getCreateAt();
        this.role=entity.getRole();
    }
}
