package fitConnect.service;

import lombok.RequiredArgsConstructor;
import fitConnect.controller.dto.ReplyRequestDto;
import fitConnect.controller.dto.response.ReplyResonseDto;
import fitConnect.entity.Comment;
import fitConnect.entity.Reply;
import fitConnect.entity.user.CustomUserDetails;
import fitConnect.entity.user.User;
import fitConnect.repository.BoardRepository;
import fitConnect.repository.CommentRepository;
import fitConnect.repository.ReplyRepository;
import fitConnect.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public ReplyResonseDto writeReply(Long commentNum,ReplyRequestDto requestDto, Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        String userId=userInfo.getUsername();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new IllegalArgumentException("로그인 하세요"));
        requestDto.setUser(user);
        Comment comment=commentRepository.findByCommentNum(commentNum).orElseThrow(()->new IllegalArgumentException("댓글이 존재하지 않습니다."));
        requestDto.setComment(comment);
        Reply reply=new Reply(requestDto);
        replyRepository.save(reply);
        return new ReplyResonseDto(reply);
    }

    public List<ReplyResonseDto> readReplies(Long commentNum){
        return replyRepository.findByCommentCommentNumOrderByModifiedAtDesc(commentNum).stream().map(ReplyResonseDto::new).toList();
    }

    @Transactional
    public ReplyResonseDto deleteReply(Long replyNum,Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        Reply reply=replyRepository.findByReplyNum(replyNum).orElseThrow(()->new IllegalArgumentException("대댓글이 존재하지 않습니다."));
        if(!reply.getUser().getUserId().equals(userInfo.getUsername())){
            throw new IllegalArgumentException("현재 로그인한 유저는 삭제할 권한이 없습니다.");
        }
        replyRepository.delete(reply);
        return new ReplyResonseDto(reply);
    }
}
