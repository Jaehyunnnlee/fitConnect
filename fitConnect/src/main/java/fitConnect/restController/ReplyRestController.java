//package fitConnect.restController;
//
//import lombok.RequiredArgsConstructor;
//import fitConnect.controller.dto.ReplyRequestDto;
//import fitConnect.controller.dto.response.ReplyResonseDto;
//import fitConnect.service.ReplyService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class ReplyRestController {
//    private final ReplyService replyService;
//    @PostMapping("/rest-api/comments/{commentNum}/replies")
//    public ResponseEntity<?> writeReply(@PathVariable("commentNum") Long commentNum, @RequestBody ReplyRequestDto requestDto, Authentication authentication){
//        try{
//            ReplyResonseDto responseDto=replyService.writeReply(commentNum,requestDto,authentication);
//            return ResponseEntity.ok(responseDto);
//        }catch (IllegalArgumentException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//    @GetMapping("/rest-api/comments/{commentNum}/replies")
//    public ResponseEntity<?> readReply(@PathVariable("commentNum") Long commentsNum){
//        List<ReplyResonseDto> replyResonseDtoList=replyService.readReplies(commentsNum);
//        return ResponseEntity.ok(replyResonseDtoList);
//    }
//
//    @Transactional
//    @DeleteMapping("/rest-api/replies/{replyNum}")
//    public ResponseEntity<?> deleteReply( @PathVariable("replyNum")Long replyNum,Authentication authentication){
//        try{
//            ReplyResonseDto responseDto=replyService.deleteReply(replyNum, authentication);
//            return ResponseEntity.ok(responseDto);
//        }catch (IllegalArgumentException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}
