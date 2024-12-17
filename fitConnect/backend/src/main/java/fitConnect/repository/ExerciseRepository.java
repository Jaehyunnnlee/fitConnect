package fitConnect.repository;

import fitConnect.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise,Long> {
    List<Exercise> findAllByExercisePart(String exercisePart);
    Optional<Exercise> findByExerciseNum(Long ExerciseNum);
}
