package fitConnect.service;

import fitConnect.dto.CommentRequestDto;
import fitConnect.dto.response.CommentResponseDto;
import fitConnect.entity.Board;
import fitConnect.entity.Comment;
import fitConnect.config.security.UserPrincipal;
import fitConnect.entity.user.User;
import fitConnect.repository.BoardRepository;
import fitConnect.repository.CommentQueryDslRepository;
import fitConnect.repository.CommentRepository;
import fitConnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentQueryDslRepository commentQueryDslRepository;
    @Transactional
    public CommentResponseDto writeComment(Long postNum,CommentRequestDto requestDto, Authentication authentication){
        UserPrincipal userInfo=(UserPrincipal) authentication.getPrincipal();
        String userId=userInfo.getUsername();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new IllegalArgumentException("로그인 하세요"));
        requestDto.setUser(user);
        Board board=boardRepository.findByPostNum(postNum).orElseThrow(()->new IllegalArgumentException("게시글이 존재하지 않습니다."));
        requestDto.setBoard(board);
        Comment comment=new Comment(requestDto);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }


    @Transactional
    public CommentResponseDto writeReply(Long postNum,Long commentNum,CommentRequestDto requestDto, Authentication authentication){
        UserPrincipal userInfo=(UserPrincipal) authentication.getPrincipal();
        String userId=userInfo.getUsername();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new IllegalArgumentException("로그인 하세요"));
        Board board=boardRepository.findByPostNum(postNum).orElseThrow(()->new IllegalArgumentException("게시글이 존재하지 않습니다"));
        Comment parent=null;
        requestDto.setParentCommentNum(commentNum);
        requestDto.setPostNum(postNum);
        if(requestDto.getParentCommentNum()!=null){
            parent=commentRepository.findByCommentNum(requestDto.getParentCommentNum())
                    .orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if (!parent.getBoard().getPostNum().equals(requestDto.getPostNum())){
                throw new IllegalArgumentException("부모댓글과 자식댓글간에 게시글이 일치하지 않습니다.");
            }
        }
        requestDto.setUser(user);
        requestDto.setBoard(board);
        if(parent!=null){
            requestDto.setParentComment(parent);
        }

        Comment comment=new Comment(requestDto);
        commentRepository.save(comment);
        if(parent!=null){
            List<Comment> children = parent.getChildren();
            children.add(comment);
            parent.setChildren(children);
            commentRepository.save(parent);
        }
        return new CommentResponseDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> readComments(Long postNum,Authentication authentication) {
        Board board=boardRepository.findByPostNum(postNum).orElseThrow(()->new IllegalArgumentException("게시글이 존재하지 않습니다."));
        List<Comment> comments = commentQueryDslRepository.findCommentByBoardPostNum(board);
        List<CommentResponseDto> responseDtoList = convertNestedStructure(comments,authentication);
        return responseDtoList;
    }
    private List<CommentResponseDto> convertNestedStructure(List<Comment> comments,Authentication authentication){
        List<CommentResponseDto> result=new ArrayList<>();
        Map<Long,CommentResponseDto> map=new HashMap<>();
        comments.forEach(c->{
            CommentResponseDto dto=new CommentResponseDto(c);
            if(authentication!=null){
                UserPrincipal userInfo=(UserPrincipal) authentication.getPrincipal();
                if(userInfo.getUsername().equals(dto.getUserId())){
                    dto.setIsAuthor(true);
                    log.info("작성자인가? {}",userInfo.getUsername().equals(dto.getUserId()));
                }
            }
            map.put(dto.getCommentNum(),dto);
            if(c.getParentComment()!=null){
                map.get(c.getParentComment().getCommentNum()).getChildren().add(dto);
            }
            else{
                result.add(dto);
            }
        });
        return result;
    }

    public List<CommentResponseDto> getCommentsByUserId(String userId){
        return commentRepository.findAllByUserUserIdOrderByModifiedAt(userId).stream().map(CommentResponseDto::new).toList();
    }

    public CommentResponseDto getComment(Long commentNum){
        return commentRepository.findByCommentNum(commentNum).map(CommentResponseDto::new).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
    }

    public CommentResponseDto updateComment(Long commentNum,CommentRequestDto requestDto,Authentication authentication){
        UserPrincipal userInfo=(UserPrincipal) authentication.getPrincipal();
        Comment comment=commentRepository.findByCommentNum(commentNum).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if(!comment.getUser().getUserId().equals(userInfo.getUsername())){
            throw new IllegalArgumentException("현재 로그인한 유저는 수정할 권한이 없습니다.");
        }
        comment.setCommentContent(requestDto.getCommentContent());
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }
    @Transactional
    public CommentResponseDto deleteComment(Long commentNum, Authentication authentication){
        UserPrincipal userInfo=(UserPrincipal) authentication.getPrincipal();
        Comment comment=commentRepository.findByCommentNum(commentNum).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if(!comment.getUser().getUserId().equals(userInfo.getUsername())){
            throw new IllegalArgumentException("현재 로그인한 유저는 삭제할 권한이 없습니다.");
        }
        commentRepository.delete(comment);
        return new CommentResponseDto(comment);
    }

}