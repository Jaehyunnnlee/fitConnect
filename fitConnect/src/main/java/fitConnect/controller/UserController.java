package fitConnect.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    @GetMapping("/signup")
    public String signUp(){
        return "user/register";
    }

    @GetMapping("/login")
    public String login(){
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/logout";
    }

    @GetMapping("/deleteUser")
    public String deleteUser(Authentication authentication){
        if(authentication==null||!authentication.isAuthenticated()){
            return "user/login";
        }
        return "user/deleteUser";
    }

    @GetMapping("/changePwd")
    public String changePwd(Authentication authentication){
        if(authentication==null||!authentication.isAuthenticated()){
            return "user/login";
        }
        return "user/changeUserPwd";
    }

    @GetMapping("/myPage")
    public String showMyPage(Authentication authentication){
        if(authentication==null||!authentication.isAuthenticated()){
            return "user/login";
        }
        return "user/userDetail";
    }
}
