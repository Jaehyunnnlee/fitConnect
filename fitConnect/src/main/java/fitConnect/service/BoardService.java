package fitConnect.service;

import lombok.RequiredArgsConstructor;
import fitConnect.controller.dto.response.BoardResponseDto;
import fitConnect.controller.dto.BoardRequestDto;
import fitConnect.entity.Board;
import fitConnect.entity.user.CustomUserDetails;
import fitConnect.entity.user.User;
import fitConnect.repository.BoardRepository;
import fitConnect.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public BoardResponseDto createPost(BoardRequestDto requestDto, Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        String userId=userInfo.getUsername();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new IllegalArgumentException("로그인 하세요"));
        requestDto.setUser(user);
        Board board=new Board(requestDto);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }
    @Transactional(readOnly = true)
    public List<BoardResponseDto> getPosts() {
        return boardRepository.findAllByOrderByModifiedAtDesc().stream().map(BoardResponseDto::new).toList();
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
