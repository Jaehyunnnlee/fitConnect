package myBlog.controller.dto;

import lombok.Getter;
import lombok.Setter;
import myBlog.entity.Board;
import myBlog.entity.Comment;
import myBlog.entity.user.User;
@Getter
@Setter
public class ReplyRequestDto {
    private Long replyNum;
    private String replyContent;
    private User user;
    private String userId;
    private Comment comment;
    private Long commentNum;

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getUserId();
    }
}
