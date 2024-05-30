package fitConnect.controller;

import fitConnect.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @GetMapping("/my-posts") public String myPosts(){
        return "board/my-posts-bootstrap";
    }
    @GetMapping("{userName}-posts")
    public String selectByUserName(@PathVariable("userName")String userName, Model model){

        model.addAttribute("userName",userName);
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

