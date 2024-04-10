package fitConnect.repository;

import fitConnect.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByOrderByModifiedAtDesc();
    Optional<List<Board>> findAllByUserUserId (String userId);
    Optional<Board> findByPostNum(Long postNum);
}
