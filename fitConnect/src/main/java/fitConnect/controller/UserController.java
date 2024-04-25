package fitConnect.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    @GetMapping("/signup-login")
    public String register(){
        return "user/signup-login-bootstrap";
    }

    @GetMapping("/test")
    public String test(){
        return "board/write-bootstrap";
    }
    @GetMapping("/test2/{postNum}")
    public String selectByPostNum(@PathVariable("postNum")Long postNum, Model model){
        model.addAttribute("postNum",postNum);
        return "board/read-bootstrap";
    }
    @GetMapping("/test2")
    public String test2(){
        return "board/read-bootstrap";
    }
    @GetMapping("/logout")
    public String logout(){
        return "redirect:/logout";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(Authentication authentication){
        if(authentication==null||!authentication.isAuthenticated()){
            return "user/signup-login-bootstrap";
        }
        return "user/delete-user-bootstrap";
    }

    @GetMapping("/changePwd")
    public String changePwd(Authentication authentication){
        if(authentication==null||!authentication.isAuthenticated()){
            return "user/signup-login-bootstrap";
        }
        return "user/change-password-bootstrap";
    }

    @GetMapping("/myPage")
    public String showMyPage(Authentication authentication){
        if(authentication==null||!authentication.isAuthenticated()){
            return "user/login";
        }
        return "user/userDetail";
    }
}
