package myBlog.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import myBlog.entity.Board;
import myBlog.entity.user.User;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long postNum;
    private String postTitle;
    private String postContent;
    private String userId;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board entity){
        this.postNum=entity.getPostNum();
        this.postTitle=entity.getPostTitle();
        this.postContent=entity.getPostContent();
        this.userId=entity.getUser().getUserId();
        this.modifiedAt = entity.getModifiedAt();
    }
}
