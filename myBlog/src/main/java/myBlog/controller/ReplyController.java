package myBlog.controller;

import lombok.RequiredArgsConstructor;
import myBlog.controller.dto.ReplyRequestDto;
import myBlog.entity.user.CustomUserDetails;
import myBlog.service.ReplyService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("/comment/{commentNum}/reply/write")
    public String writeReply(@PathVariable("commentNum") Long commentNum, @ModelAttribute("replyForm") ReplyRequestDto requestDto, Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        if(userInfo==null){
            return "user/login";
        }
        replyService.writeReply(requestDto,authentication);
        return "redirect:/comment/"+commentNum;
    }

    @Transactional
    @PostMapping("/comment/{commentNum}/reply/{replyNum}/delete")
    public String deleteReply(@PathVariable("commentNum") Long commentNum,@PathVariable("replyNum")Long replyNum,Authentication authentication){
        try{
            replyService.deleteReply(replyNum, authentication);
            return "redirect:/comment/"+commentNum;
        }catch (IllegalArgumentException ex){
            return "reply/deleteReply";
        }
    }

    @GetMapping("/comment/{commentNum}/reply/{replyNum}/delete")
    public String deleteReply(@PathVariable("commentNum") Long commentNum,@PathVariable("replyNum") Long replyNum,Model model){
        model.addAttribute("commentNum",commentNum);
        model.addAttribute("replyNum",replyNum);
        return "reply/deleteReply";
    }
}
