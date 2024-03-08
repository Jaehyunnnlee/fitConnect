package myBlog.repository;

import myBlog.entity.Board;
import myBlog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByBoardPostNumOrderByModifiedAtAsc(Long postNum);
    List<Comment> findAllByUserUserIdOrderByModifiedAt(String userId);
    Optional<Comment> findByCommentNum(Long postNum);
}
