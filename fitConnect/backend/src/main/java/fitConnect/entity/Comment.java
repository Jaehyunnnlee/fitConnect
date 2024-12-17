package fitConnect.entity;

import fitConnect.dto.CommentRequestDto;
import fitConnect.entity.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Comment extends Timestamped{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="comment_num",nullable = false)
    private long commentNum;

    @Column(name="comment_content",nullable = false)
    @Lob
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_user_id", referencedColumnName = "user_id", columnDefinition = "VARCHAR(255)", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_post_num", referencedColumnName = "post_num", columnDefinition = "BIGINT", nullable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_comment_num")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    private List<Comment> children=new ArrayList<>();

    public Comment(CommentRequestDto requestDto){
        this.commentContent=requestDto.getCommentContent();
        this.user=requestDto.getUser();
        this.board=requestDto.getBoard();
        this.parentComment = requestDto.getParentComment(); // 부모 댓글 설정
    }
}
