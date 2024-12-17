package fitConnect.dto.response;

import fitConnect.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class BoardPageResponseDto {
    private List<BoardSummaryResponseDto> posts;
    private long totalElements;
    private int totalPages;
    private boolean first;
    private boolean last;
    private boolean empty;

    public BoardPageResponseDto(Page<Board> postPage){
        this.posts=postPage.getContent().stream()
                .map(BoardSummaryResponseDto::new)
                .collect(Collectors.toList());
        this.totalPages=postPage.getTotalPages();
        this.totalElements=postPage.getTotalElements();
        this.first=postPage.isFirst();
        this.last=postPage.isLast();
        this.empty=postPage.isEmpty();
    }
}
