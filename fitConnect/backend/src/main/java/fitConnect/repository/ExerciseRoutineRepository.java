package fitConnect.repository;

import fitConnect.entity.exerciseRoutine.ExerciseRoutine;
import fitConnect.entity.exerciseRoutine.ExerciseRoutinePK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRoutineRepository extends JpaRepository<ExerciseRoutine, ExerciseRoutinePK> {
    List<ExerciseRoutine> findByExerciseRoutinePKRoutineRoutineNum(Long routineNum);
}
