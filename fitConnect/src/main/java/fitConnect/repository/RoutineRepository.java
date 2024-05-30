package fitConnect.repository;

import fitConnect.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine,Long> {
    List<Routine> findAllByUserUserIdOrderByModifiedAtDesc(String userId);
    Optional<Routine> findByRoutineNum(Long routineNum);
    Optional<Routine> findByUserUserIdAndRoutineNum(String userId,Long routineNum);
}
