package fitConnect.dto.response;

import fitConnect.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentNum;
    private String commentContent;
    private String userId;
    private String userName;
    private LocalDateTime modifiedAt;
    private Long postNum;
    private Long parentCommentNum;
    private List<CommentResponseDto> children;
    private boolean isAuthor;

    public CommentResponseDto(Comment entity){
        this.commentNum=entity.getCommentNum();
        this.commentContent=entity.getCommentContent();
        this.userId=entity.getUser().getUserId();
        this.userName=entity.getUser().getUserName();
        this.modifiedAt=entity.getModifiedAt();
        this.postNum=entity.getBoard().getPostNum();
        if(entity.getParentComment()!=null) {
            this.parentCommentNum = entity.getParentComment().getCommentNum();
        }
        this.children=new ArrayList<>();
        this.isAuthor=false;
    }

    public void setIsAuthor(boolean isAuthor) {
        this.isAuthor = isAuthor;
    }
}
