package fitConnect.dto;

import lombok.Getter;
import lombok.Setter;
import fitConnect.entity.user.User;


@Getter
@Setter
public class BoardRequestDto {
    private Long postNum;
    private String postTitle;
    private String postContent;
}
