package fitConnect.controller.dto;

import lombok.Getter;
import lombok.Setter;
import fitConnect.entity.Board;
import fitConnect.entity.user.User;

@Getter
@Setter
public class CommentRequestDto {
    private Long commentNum;
    private String commentContent;
    private User user;
    private String userId;
    private Board board;
    private Long postNum;

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getUserId();
    }
}
