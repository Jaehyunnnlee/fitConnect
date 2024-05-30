package fitConnect.controller.dto.response;

import fitConnect.entity.Exercise;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseResponseDto {
    private Long exerciseNum;
    private String exerciseName;
    private String exercisePart;

    public ExerciseResponseDto(Exercise entity){
        this.exerciseNum=entity.getExerciseNum();
        this.exerciseName=entity.getExerciseName();
        this.exercisePart=entity.getExercisePart();
    }
}
