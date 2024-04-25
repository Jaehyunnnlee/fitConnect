//package fitConnect.repository;
//
//import fitConnect.entity.Reply;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface ReplyRepository extends JpaRepository<Reply,Long> {
//    List<Reply> findByCommentCommentNumOrderByModifiedAtDesc(Long commentNum);
//    Optional<Reply> findByReplyNum(Long replyNum);
//}
