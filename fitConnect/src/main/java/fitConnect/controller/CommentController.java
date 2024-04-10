package fitConnect.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class CommentController {
    @GetMapping("posts/{postNum}/comments/{commentNum}")
    public String readComment(@PathVariable ("postNum") Long postNum,@PathVariable("commentNum") Long commentNum, Model model){
        model.addAttribute("postNum",postNum);
        model.addAttribute("commentNum",commentNum);
        return "comment/commentDetail";
    }

    @GetMapping("/posts/{postNum}/comments/{commentNum}/delete")
    public String deleteComment(@PathVariable("postNum") Long postNum, @PathVariable("commentNum") Long commentNum,Model model){
        model.addAttribute("postNum",postNum);
        model.addAttribute("commentNum",commentNum);
        return "comment/deleteComment";
    }
    @GetMapping("/comments/my-comments")
    public String myComments(){
        return "comment/myComments";
    }
}
