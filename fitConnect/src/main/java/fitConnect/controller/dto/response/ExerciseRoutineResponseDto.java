package fitConnect.controller.dto.response;

import fitConnect.entity.exerciseRoutine.ExerciseRoutine;
import lombok.Getter;


@Getter
public class ExerciseRoutineResponseDto {
    private Long routineNum;
    private Long exerciseNum;
    private String exerciseName;
    private int sets;
    private int reps;
    private int weight;

    public ExerciseRoutineResponseDto(ExerciseRoutine entity){
        this.routineNum=entity.getExerciseRoutinePK().getRoutine().getRoutineNum();
        this.exerciseNum=entity.getExerciseRoutinePK().getExercise().getExerciseNum();
        this.exerciseName=entity.getExerciseRoutinePK().getExercise().getExerciseName();
        this.sets=entity.getSets();
        this.reps=entity.getReps();
        this.weight=entity.getWeight();
    }
}
