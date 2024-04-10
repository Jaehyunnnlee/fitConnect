package fitConnect.controller.dto;

import lombok.Getter;
import lombok.Setter;
import fitConnect.entity.Comment;
import fitConnect.entity.user.User;
@Getter
@Setter
public class ReplyRequestDto {
    private Long replyNum;
    private String replyContent;
    private User user;
    private String userName;
    private Comment comment;
    private Long commentNum;

    public void setUser(User user) {
        this.user = user;
        this.userName = user.getUserName();
    }
}
