package fitConnect.entity.exerciseRoutine;

import fitConnect.dto.ExerciseRoutineRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ExerciseRoutine {
    @EmbeddedId
    private ExerciseRoutinePK exerciseRoutinePK;

    @Column(name="sets",nullable = false)
    private int sets;

    @Column(name="reps",nullable = false)
    private int reps;

    @Column(name="weight",nullable = false)
    private int weight;

    public ExerciseRoutine(ExerciseRoutineRequestDto requestDto){
        this.exerciseRoutinePK=requestDto.getExerciseRoutinePK();
        this.sets=requestDto.getSets();
        this.reps=requestDto.getReps();
        this.weight=requestDto.getWeight();
    }
}
