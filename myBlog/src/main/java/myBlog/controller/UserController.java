package myBlog.controller;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import myBlog.controller.dto.UserRequestDto;
import myBlog.controller.dto.response.BoardResponseDto;
import myBlog.controller.dto.response.UserResponseDto;
import myBlog.entity.user.CustomUserDetails;
import myBlog.service.CommentService;
import myBlog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    public String signupAccount(@ModelAttribute("signupForm") UserRequestDto requestDto,Model model) {
        try {
            userService.signupAccount(requestDto);
            return "user/login";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/register";
        }
    }

    @GetMapping("/myPage")
    public String myPage(Model model, Authentication authentication){
        if(authentication == null||!authentication.isAuthenticated()){
            return "user/login";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        BoardResponseDto postInfo=(BoardResponseDto) model.getAttribute("myPost");
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("myPost",postInfo);
        return "/user/userDetail";
    }

    @PostMapping ("/changePwd")
    public String changePassword(@ModelAttribute("changePwdForm")UserRequestDto userRequestDto,Authentication authentication){
        userService.changePassword(userRequestDto,authentication);
        return "index/home";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(@ModelAttribute("deleteUserForm")UserRequestDto userRequestDto,Authentication authentication){
        userService.deleteUser(userRequestDto,authentication);
        return "redirect:/logout";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(Authentication authentication){
        if(authentication==null||!authentication.isAuthenticated()){
            return "user/login";
        }
        return "/user/deleteUser";
    }


    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        // Implementing a simple ping-pong response
        String pong = "Pong!";
        return ResponseEntity.ok(pong);
    }
}
