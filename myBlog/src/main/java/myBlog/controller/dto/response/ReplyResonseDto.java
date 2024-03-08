package myBlog.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import myBlog.entity.Board;
import myBlog.entity.Reply;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ReplyResonseDto {
    private Long replyNum;
    private String replyContent;
    private String userId;
    private LocalDateTime modifiedAt;
    private Long commentNum;

    public ReplyResonseDto(Reply entity){
        this.replyNum=entity.getReplyNum();
        this.replyContent=entity.getReplyContent();
        this.userId=entity.getUser().getUserId();
        this.modifiedAt = entity.getModifiedAt();
        this.commentNum=entity.getComment().getCommentNum();
    }
}
