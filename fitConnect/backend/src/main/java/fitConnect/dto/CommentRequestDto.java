package fitConnect.dto;

import fitConnect.entity.Board;
import fitConnect.entity.Comment;
import fitConnect.entity.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentRequestDto {
    private Long commentNum;
    private String commentContent;
    private User user;
    private String userName;
    private Board board;
    private Long postNum;
    private Comment parentComment;
    private Long parentCommentNum;
    private List<Comment> children;
    public void setUser(User user) {
        this.user = user;
        this.userName = user.getUserName();
    }
}
