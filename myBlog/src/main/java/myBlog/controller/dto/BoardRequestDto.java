package myBlog.controller.dto;

import lombok.Getter;
import lombok.Setter;
import myBlog.entity.user.User;


@Getter
@Setter
public class BoardRequestDto {
    private Long postNum;
    private String postTitle;
    private String postContent;
    private User user;
    private String userId;

    public void setUser(User user) {
        this.user = user;
        this.userId = user.getUserId();
    }
}
