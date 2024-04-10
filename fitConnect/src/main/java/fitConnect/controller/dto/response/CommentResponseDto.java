package fitConnect.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import fitConnect.entity.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentNum;
    private String commentContent;
    private String userName;
    private LocalDateTime modifiedAt;
    private Long postNum;

    public CommentResponseDto(Comment entity){
        this.commentNum=entity.getCommentNum();
        this.commentContent=entity.getCommentContent();
        this.userName=entity.getUser().getUserName();
        this.modifiedAt=entity.getModifiedAt();
        this.postNum=entity.getBoard().getPostNum();
    }
}
