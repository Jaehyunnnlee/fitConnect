package fitConnect.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import fitConnect.entity.Board;
import fitConnect.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static fitConnect.entity.QComment.comment;

@Repository
public class CommentQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    @Autowired
    public CommentQueryDslRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<Comment> findCommentByBoardPostNum(Board board) {
        return queryFactory.selectFrom(comment)
                .leftJoin(comment.parentComment)
                .fetchJoin()
                .where(comment.board.postNum.eq(board.getPostNum()))
                .orderBy(comment.parentComment.commentNum.asc().nullsFirst(),
                        comment.modifiedAt.desc()
                ).fetch();
    }

    public List<Comment> findAllReplies(Long parentCommentNum) {
        return queryFactory.selectFrom(comment)
                .where(comment.parentComment.commentNum.eq(parentCommentNum))
                .orderBy(comment.createAt.asc())
                .fetch();
    }
}
