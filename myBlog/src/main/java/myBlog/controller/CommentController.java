package myBlog.controller;

import lombok.RequiredArgsConstructor;
import myBlog.controller.dto.CommentRequestDto;
import myBlog.controller.dto.response.CommentResponseDto;
import myBlog.controller.dto.response.ReplyResonseDto;
import myBlog.entity.Reply;
import myBlog.entity.user.CustomUserDetails;
import myBlog.service.BoardService;
import myBlog.service.CommentService;
import myBlog.service.ReplyService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final ReplyService replyService;
    @PostMapping("/board/post/{id}/comment/write")
    public String writeComment(@PathVariable("id") Long postNum,@ModelAttribute("commentForm")CommentRequestDto requestDto, Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        if(userInfo==null){
            return "user/login";
        }
        commentService.writeComment(requestDto,authentication);
        return "redirect:/board/post/"+postNum;
    }

    @GetMapping("/comment/myComments/{userId}")
    public String myComments(@PathVariable String userId,Model model){
        List<CommentResponseDto> myComments=commentService.getCommentsByUserId(userId);
        model.addAttribute("myComments",myComments);
        return "comment/myComments";
    }

    @GetMapping("/comment/{commentNum}")
    public String getComment(@PathVariable Long commentNum,Model model,Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        model.addAttribute("userInfo",userInfo);
        CommentResponseDto comment=commentService.getComment(commentNum);
        List<ReplyResonseDto> replyList=replyService.readReplies(commentNum);
        model.addAttribute("comment",comment);
        model.addAttribute("replyList",replyList);
        return "comment/commentDetail";
    }

    @Transactional
    @PostMapping("/board/post/{postNum}/deleteComment/{commentNum}")
    public String deleteComment(@PathVariable("postNum") Long postNum,@PathVariable("commentNum") Long commentNum,Authentication authentication){
        try{
            commentService.deleteComment(commentNum, authentication);
            return "redirect:/board/post/"+postNum;
        }catch (IllegalArgumentException ex){
            return "comment/deleteComment";
        }
    }

    @GetMapping("/board/post/{postNum}/deleteComment/{commentNum}")
    public String deleteComment(@PathVariable("postNum") Long postNum,@PathVariable("commentNum") Long commentNum,Model model){
        model.addAttribute("postNum",postNum);
        model.addAttribute("commentNum",commentNum);
        return "comment/deleteComment";
    }
}
