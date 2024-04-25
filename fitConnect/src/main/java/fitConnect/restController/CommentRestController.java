package fitConnect.restController;

import lombok.RequiredArgsConstructor;
import fitConnect.controller.dto.CommentRequestDto;
import fitConnect.controller.dto.response.CommentResponseDto;
import fitConnect.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentRestController {
    private final CommentService commentService;

    @PostMapping("/rest-api/posts/{id}/comments")
    public ResponseEntity<?> writeComment(@PathVariable("id") Long postNum, @RequestBody CommentRequestDto requestDto, Authentication authentication) {
        try{
            CommentResponseDto commentResponseDto = commentService.writeComment(postNum,requestDto, authentication);
            return ResponseEntity.ok(commentResponseDto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping("/rest-api/posts/{id}/comments/{commentNum}/replies")
    public ResponseEntity<?> writeReply(@PathVariable("id") Long postNum,@PathVariable("commentNum") Long commentNum,@RequestBody CommentRequestDto requestDto, Authentication authentication){
        try{
            CommentResponseDto commentResponseDto = commentService.writeReply(postNum,commentNum,requestDto, authentication);
            return ResponseEntity.ok(commentResponseDto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/rest-api/posts/{id}/comments/{commentNum}/replies")
    public ResponseEntity<?> readReplies(@PathVariable("commentNum") Long commentNum){
        List<CommentResponseDto> replyResponseDtoList=commentService.readReplies(commentNum);
        return ResponseEntity.ok(replyResponseDtoList);
    }

    @GetMapping("/rest-api/posts/{id}/comments")
    public ResponseEntity<?> readComments(@PathVariable("id") Long postNum){
            List<CommentResponseDto> commentResponseDtoList=commentService.readComments(postNum);
            return ResponseEntity.ok(commentResponseDtoList);
    }


    @GetMapping("/rest-api/comments/users/{userId}/my-comments")
    public ResponseEntity<?> myComments(@PathVariable("userId") String userId){
        try{
            List<CommentResponseDto> myComments=commentService.getCommentsByUserId(userId);
            return ResponseEntity.ok(myComments);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/rest-api/comments/{commentNum}")
    public ResponseEntity<?> getComment(@PathVariable Long commentNum){
        try{
            CommentResponseDto comment=commentService.getComment(commentNum);
            return ResponseEntity.ok(comment);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @DeleteMapping("/rest-api/comments/{commentNum}")
    public ResponseEntity<?> deleteComment(@PathVariable("commentNum") Long commentNum,Authentication authentication){
        try{
            CommentResponseDto comment=commentService.deleteComment(commentNum, authentication);
            return ResponseEntity.ok(comment);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
