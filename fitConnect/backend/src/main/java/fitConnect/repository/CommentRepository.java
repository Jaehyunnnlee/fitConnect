package fitConnect.repository;

import fitConnect.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long>, QuerydslPredicateExecutor<Comment> {
    List<Comment> findByBoardPostNumOrderByModifiedAtAsc(Long postNum);
    List<Comment> findAllByUserUserIdOrderByModifiedAt(String userId);
    Optional<Comment> findByCommentNum(Long postNum);
}
