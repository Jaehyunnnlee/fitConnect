package fitConnect.controller;

import lombok.RequiredArgsConstructor;
import fitConnect.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/post")
    public String writePost() {
        return "board/write-bootstrap";
    }
    @GetMapping("/posts")
    public String readPost(){
        return "board/readPost";
    }
    @GetMapping("/my-posts") String myPosts(){
        return "board/my-posts-bootstrap";
    }
    @GetMapping("/post/{postNum}")
    public String selectByPostNum(@PathVariable("postNum")Long postNum, Model model){
        model.addAttribute("postNum",postNum);
        return "board/read-bootstrap";
    }
    @GetMapping("/post/{postNum}/edit")
    public String updatePost(@PathVariable("postNum")Long postNum,Model model){
        model.addAttribute("postNum",postNum);
        return "board/edit-bootstrap";
    }

    @GetMapping("/post/{postNum}/delete")
    public String deletePost(@PathVariable("postNum") Long postNum, Model model){
        model.addAttribute("postNum",postNum);
        return "board/deletePost";
    }
}

