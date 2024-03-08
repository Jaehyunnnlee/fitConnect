package myBlog.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import myBlog.controller.dto.response.BoardResponseDto;
import myBlog.controller.dto.BoardRequestDto;
import myBlog.controller.dto.response.UserResponseDto;
import myBlog.entity.Board;
import myBlog.entity.user.CustomUserDetails;
import myBlog.entity.user.User;
import myBlog.repository.BoardRepository;
import myBlog.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return boardRepository.findAllByOrderByModifiedAtAsc().stream().map(BoardResponseDto::new).toList();
    }

    @Transactional
    public BoardResponseDto getPost(Long postNum){
        return boardRepository.findByPostNum(postNum).map(BoardResponseDto::new).orElseThrow(
                ()->new IllegalArgumentException("게시글 존재하지 않습니다.")
        );
    }

    public List<BoardResponseDto> getPostsByUserId(String userId) {
        return boardRepository.findAllByUserUserId(userId).stream().map(BoardResponseDto::new).toList();
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
