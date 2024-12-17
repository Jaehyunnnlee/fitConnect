package fitConnect.service;

import fitConnect.config.security.UserPrincipal;
import fitConnect.controller.dto.response.BoardPageResponseDto;
import fitConnect.controller.dto.response.BoardSummaryResponseDto;
import lombok.RequiredArgsConstructor;
import fitConnect.controller.dto.response.BoardResponseDto;
import fitConnect.controller.dto.BoardRequestDto;
import fitConnect.entity.Board;
import fitConnect.config.security.CustomUserDetails;
import fitConnect.entity.user.User;
import fitConnect.repository.BoardRepository;
import fitConnect.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public boolean isAuthor(Long postNum,String username){
        BoardResponseDto post=boardRepository.findByPostNum(postNum).map(BoardResponseDto::new).
                orElseThrow(()->new IllegalArgumentException("게시글이 존재하지 않습니다."));
        return post.getUserName().equals(username);

    }
    @Transactional
    public BoardResponseDto createPost(BoardRequestDto requestDto, Authentication authentication){
//        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        UserPrincipal userInfo=(UserPrincipal) authentication.getPrincipal();
        String userId=userInfo.getUsername();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new IllegalArgumentException("로그인 하세요"));
        Board board=new Board(requestDto,user);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }
//    @Transactional(readOnly = true)
//    public List<BoardResponseDto> getPosts() {
//        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
//    }

    public BoardPageResponseDto getPosts(int page, int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Board> postPage=boardRepository.findAllByOrderByModifiedAtDesc(pageable);
        log.info("pagenation요소 : {}",postPage.getContent());
        log.info("totalPage: {}",postPage.getTotalPages());
        log.info("totalElement: {}",postPage.getTotalElements());
        log.info("Pageable: {}",postPage.getPageable());
        return new BoardPageResponseDto(postPage);
    }

    @Transactional
    public BoardResponseDto getPost(Long postNum){
        return boardRepository.findByPostNum(postNum).map(BoardResponseDto::new).orElseThrow(
                ()->new IllegalArgumentException("게시글 존재하지 않습니다.")
        );
    }

    public Optional<List<BoardResponseDto>> getPostsByUserName(String userName) {
        return boardRepository.findAllByUserUserName(userName)
                .map(boards -> boards.stream().map(BoardResponseDto::new).toList());
    }

    @Transactional
    public BoardResponseDto deletePost(Long postNum,Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();

        Board board=boardRepository.findByPostNum(postNum).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if(!board.getUser().getUserId().equals(userInfo.getUsername())){
            throw new IllegalArgumentException("현재 로그인한 유저는 삭제할 권한이 없습니다.");
        }
        boardRepository.delete(board);
        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updatePost(Long postNum,Authentication authentication,BoardRequestDto requestDto){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        Board board=boardRepository.findByPostNum(postNum).orElseThrow(()->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        board.setPostTitle(requestDto.getPostTitle());
        board.setPostContent(requestDto.getPostContent());
        if(!board.getUser().getUserId().equals(userInfo.getUsername())){
            throw new IllegalArgumentException("현재 로그인한 유저는 수정할 권한이 없습니다.");
        }
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }
}
