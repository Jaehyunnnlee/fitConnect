package fitConnect.service;

import fitConnect.controller.dto.CommentRequestDto;
import fitConnect.controller.dto.response.CommentResponseDto;
import fitConnect.entity.Board;
import fitConnect.entity.Comment;
import fitConnect.entity.user.CustomUserDetails;
import fitConnect.entity.user.User;
import fitConnect.repository.BoardRepository;
import fitConnect.repository.CommentQueryDslRepository;
import fitConnect.repository.CommentRepository;
import fitConnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final CommentQueryDslRepository commentQueryDslRepository;
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


    @Transactional
    public CommentResponseDto writeReply(Long postNum,Long commentNum,CommentRequestDto requestDto, Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        String userId=userInfo.getUsername();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new IllegalArgumentException("로그인 하세요"));
        Board board=boardRepository.findByPostNum(postNum).orElseThrow(()->new IllegalArgumentException("게시글이 존재하지 않습니다"));
        Comment parent=null;
        requestDto.setParentCommentNum(commentNum);
        requestDto.setPostNum(postNum);
        if(requestDto.getParentCommentNum()!=null){
            parent=commentRepository.findByCommentNum(requestDto.getParentCommentNum())
                    .orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if (parent.getBoard().getPostNum()!=requestDto.getPostNum()){
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
    public List<CommentResponseDto> readComments(Long postNum) {
        Board board=boardRepository.findByPostNum(postNum).orElseThrow(()->new IllegalArgumentException("게시글이 존재하지 않습니다."));
        List<Comment> topLevelComments = commentQueryDslRepository.findAllTopLevelComments(board);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        for (Comment topLevelComment : topLevelComments) {
            CommentResponseDto responseDto = new CommentResponseDto(topLevelComment);
            responseDto.setChildren(readReplies(topLevelComment.getCommentNum()));
            responseDtoList.add(responseDto);
        }

        return responseDtoList;
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> readReplies(Long parentCommentNum) {
        List<Comment> replies = commentQueryDslRepository.findAllReplies(parentCommentNum);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();

        for (Comment reply : replies) {
            CommentResponseDto responseDto = new CommentResponseDto(reply);
            //만일 무한 계층을 만들고 싶을때 활성화하면 됨
            //현재는 답글을 2계층으로 제한중
            //responseDto.setChildren(readReplies(reply.getCommentNum()));
            responseDtoList.add(responseDto);
        }
        return responseDtoList;
    }

    private List<CommentResponseDto> mapToResponseDtoList(List<Comment> commentList) {
        return commentList.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
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