package fitConnect.controller.dto;

import lombok.Getter;
import lombok.Setter;
import fitConnect.entity.user.Role;

@Getter
@Setter
public class UserRequestDto {
    private Long userNum;
    private String userId;
    private String userPwd;
    private String userName;
    private String userPwdConfirm;
    private Role role;
    private String newPassword;
}
