package fitConnect.dto;

import fitConnect.entity.Exercise;
import fitConnect.entity.Routine;
import fitConnect.entity.exerciseRoutine.ExerciseRoutinePK;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExerciseRoutineRequestDto {
    private ExerciseRoutinePK exerciseRoutinePK;
    private Routine routine;
    private Exercise exercise;
    private Long routineNum;
    private Long exerciseNum;
    private int reps;
    private int sets;
    private int weight;
    private double oneRepMax;

    public void setRoutineAndExercise(Routine routine,Exercise exercise){
        this.routine=routine;
        this.routineNum=routine.getRoutineNum();
        this.exercise=exercise;
        this.exerciseNum=exercise.getExerciseNum();
        this.exerciseRoutinePK=new ExerciseRoutinePK(routine,exercise);
    }
}
