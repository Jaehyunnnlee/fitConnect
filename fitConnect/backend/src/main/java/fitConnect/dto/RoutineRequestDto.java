package fitConnect.dto;

import fitConnect.entity.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoutineRequestDto {
    private String routineTitle;
    private User user;
    private String userId;
    private String routineDescription;
    private boolean completed;

    public void setUser(User user){
        this.user=user;
        this.userId=user.getUserId();
    }
}
