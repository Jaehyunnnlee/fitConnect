package fitConnect.controller.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import fitConnect.entity.Board;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Long postNum;
    private String postTitle;
    private String postContent;
    private String userName;
    private LocalDateTime modifiedAt;

    public BoardResponseDto(Board entity){
        this.postNum=entity.getPostNum();
        this.postTitle=entity.getPostTitle();
        this.postContent=entity.getPostContent();
        this.userName=entity.getUser().getUserName();
        this.modifiedAt = entity.getModifiedAt();
    }
}
