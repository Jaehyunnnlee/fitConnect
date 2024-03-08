package myBlog.repository;

import myBlog.entity.Board;
import myBlog.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByOrderByModifiedAtAsc();
    List<Board> findAllByUserUserId (String userId);
    Optional<Board> findByPostNum(Long postNum);
}
