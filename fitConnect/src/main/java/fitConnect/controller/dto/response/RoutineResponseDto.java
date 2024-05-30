package fitConnect.controller.dto.response;

import fitConnect.entity.Routine;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RoutineResponseDto {
    private Long routineNum;
    private String routineTitle;
    private String routineDescription;
    private String userName;
    private String userId;
    private LocalDateTime createdAt;
    private boolean completed;

    public RoutineResponseDto(Routine entity){
        this.routineNum=entity.getRoutineNum();
        this.routineTitle=entity.getRoutineTitle();
        this.routineDescription=entity.getRoutineDescription();
        this.userName=entity.getUser().getUserName();
        this.userId=entity.getUser().getUserId();
        this.createdAt=entity.getCreateAt();
        this.completed=entity.isCompleted();
    }
}
