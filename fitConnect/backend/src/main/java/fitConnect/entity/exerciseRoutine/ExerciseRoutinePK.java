package fitConnect.entity.exerciseRoutine;

import fitConnect.entity.Exercise;
import fitConnect.entity.Routine;
import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
public class ExerciseRoutinePK implements Serializable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exercise_id",nullable = false)
    private Exercise exercise;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="routine_id",nullable = false)
    private Routine routine;

    public ExerciseRoutinePK(Routine routine, Exercise exercise) {
        this.routine = routine;
        this.exercise = exercise;
    }
}
