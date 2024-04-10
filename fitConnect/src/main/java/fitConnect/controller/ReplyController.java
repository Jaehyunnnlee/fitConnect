package fitConnect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    @GetMapping("posts/{postNum}/comment/{commentNum}/reply/{replyNum}/delete")
    public String deleteReply(@PathVariable ("postNum") Long postNum,@PathVariable("commentNum") Long commentNum,@PathVariable("replyNum") Long replyNum,Model model){
        model.addAttribute(postNum);
        model.addAttribute("commentNum",commentNum);
        model.addAttribute("replyNum",replyNum);
        return "reply/deleteReply";
    }
}
