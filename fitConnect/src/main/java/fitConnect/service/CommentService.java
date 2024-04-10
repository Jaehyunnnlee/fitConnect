package fitConnect.service;

import lombok.RequiredArgsConstructor;
import fitConnect.controller.dto.CommentRequestDto;
import fitConnect.controller.dto.response.CommentResponseDto;
import fitConnect.entity.Board;
import fitConnect.entity.Comment;
import fitConnect.entity.user.CustomUserDetails;
import fitConnect.entity.user.User;
import fitConnect.repository.BoardRepository;
import fitConnect.repository.CommentRepository;
import fitConnect.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    @Transactional
    public CommentResponseDto writeComment(Long postNum,CommentRequestDto requestDto, Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        String userId=userInfo.getUsername();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new IllegalArgumentException("로그인 하세요"));
        requestDto.setUser(user);
        Board board=boardRepository.findByPostNum(postNum).orElseThrow(()->new IllegalArgumentException("게시글이 존재하지 않습니다."));
        requestDto.setBoard(board);
        Comment comment=new Comment(requestDto);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }
    @Transactional(readOnly = true)
    public List<CommentResponseDto> readComments(Long postNum){
        return commentRepository.findByBoardPostNumOrderByModifiedAtDesc(postNum).stream().map(CommentResponseDto::new).toList();
    }


    public List<CommentResponseDto> getCommentsByUserId(String userId){
        return commentRepository.findAllByUserUserIdOrderByModifiedAt(userId).stream().map(CommentResponseDto::new).toList();
    }

    public CommentResponseDto getComment(Long commentNum){
        return commentRepository.findByCommentNum(commentNum).map(CommentResponseDto::new).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
    }

    @Transactional
    public CommentResponseDto deleteComment(Long commentNum, Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        Comment comment=commentRepository.findByCommentNum(commentNum).orElseThrow(()->new IllegalArgumentException("대댓글이 존재하지 않습니다."));
        if(!comment.getUser().getUserId().equals(userInfo.getUsername())){
            throw new IllegalArgumentException("현재 로그인한 유저는 삭제할 권한이 없습니다.");
        }
        commentRepository.delete(comment);
        return new CommentResponseDto(comment);
    }

}