package myBlog.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import myBlog.controller.dto.BoardRequestDto;
import myBlog.controller.dto.response.BoardResponseDto;
import myBlog.controller.dto.response.CommentResponseDto;
import myBlog.controller.dto.response.UserResponseDto;
import myBlog.entity.Comment;
import myBlog.entity.Reply;
import myBlog.entity.user.CustomUserDetails;
import myBlog.service.BoardService;
import myBlog.service.CommentService;
import myBlog.service.ReplyService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("post")
    public String showBoard(Model model,HttpSession session){
        UserResponseDto userInfo=(UserResponseDto)session.getAttribute("userInfo");
        model.addAttribute("userInfo",userInfo);
        return "board/writePost";
    }

    @GetMapping("/posts")
    public String getPosts(Model model){
        List<BoardResponseDto> postList=boardService.getPosts();
        model.addAttribute("postList",postList);
        return "board/readPost";
    }


    @PostMapping("/post")
    public String createPost(@ModelAttribute("writeForm")BoardRequestDto requestDto, Authentication authentication){
        CustomUserDetails userInfo = (CustomUserDetails) authentication.getPrincipal();
        if(userInfo==null){
            return "user/login";
        }
        boardService.createPost(requestDto,authentication);
        return "redirect:/board/posts";
    }


    @GetMapping("/post/{id}")
    public String getPost(@PathVariable Long id,Model model,Authentication authentication){
        BoardResponseDto post=boardService.getPost(id);
        if(authentication !=null){
            CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
            model.addAttribute("userInfo",userInfo);
        }
        model.addAttribute("post",post);
        List<CommentResponseDto> commentList=commentService.readComments(id);
        model.addAttribute("commentList",commentList);
        return "board/postDetail";
    }

    @GetMapping ("posts/{userId}")
    public String getPostByUserName(@PathVariable String userId,Model model){
        List<BoardResponseDto> myPost=boardService.getPostsByUserId(userId);
        model.addAttribute("myPosts",myPost);
        return "board/myPosts";
    }

    @Transactional
    @PostMapping("/deletePost/{postNum}")
    public String deletePost(@PathVariable Long postNum,Authentication authentication){
        try{
            boardService.deletePost(postNum, authentication);
            return "redirect:/board/posts";
        }catch (IllegalArgumentException ex){
            return "board/deletePost";
        }
    }

    @GetMapping("/deletePost/{postNum}")
    public String deletePost(@PathVariable Long postNum, Model model){
        model.addAttribute("postNum",postNum);
        return"board/deletePost";
    }

    @GetMapping("/updatePost/{postNum}")
    public String updatePost(@PathVariable Long postNum,Model model){
        model.addAttribute("postNum",postNum);
        return "board/updatePost";
    }

    @PostMapping("/updatePost/{postNum}")
    public String updatePost(@PathVariable Long postNum,Authentication authentication,@ModelAttribute("updatePost") BoardRequestDto requestDto){
        try {
            boardService.updatePost(postNum,authentication,requestDto);
            return "redirect:/board/posts";
        }catch (IllegalArgumentException ex){
            return "home";
        }
    }
}
