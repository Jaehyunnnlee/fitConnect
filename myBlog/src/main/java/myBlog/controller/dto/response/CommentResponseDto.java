package myBlog.controller.dto.response;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myBlog.entity.Board;
import myBlog.entity.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentNum;
    private String commentContent;
    private String userId;
    private LocalDateTime modifiedAt;
    private Long postNum;

    public CommentResponseDto(Comment entity){
        this.commentNum=entity.getCommentNum();
        this.commentContent=entity.getCommentContent();
        this.userId=entity.getUser().getUserId();
        this.modifiedAt=entity.getModifiedAt();
        this.postNum=entity.getBoard().getPostNum();
    }
}
