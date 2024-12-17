package fitConnect.dto.response;

import fitConnect.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class BoardSummaryResponseDto {
    private Long postNum;
    private String postTitle;
    private String userName;
    private LocalDateTime modifiedAt;

    public BoardSummaryResponseDto(Board board){
        this.postNum=board.getPostNum();
        this.postTitle=board.getPostTitle();
        this.userName=board.getUser().getUserName();
        this.modifiedAt=board.getModifiedAt();
    }
}
