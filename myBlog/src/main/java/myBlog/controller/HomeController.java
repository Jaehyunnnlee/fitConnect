package myBlog.controller;




import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(){
        return "index/home";
    }


    @GetMapping("/signup")
    public String signUp(Model model,HttpSession session){
        String errorMessage=(String) session.getAttribute("errorMessage");
        if(errorMessage!=null){
            model.addAttribute("errorMessage",errorMessage);
            session.removeAttribute("errorMessage");
        }
        return "user/register";
    }

    @GetMapping("/login")
    public String login(Model model,HttpSession session){
        String errorMessage=(String) session.getAttribute("errorMessage");
        if(errorMessage!=null){
            model.addAttribute("errorMessage",errorMessage);
            session.removeAttribute("errorMessage");
        }
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(){
        return "redirect:/logout";
    }

    @GetMapping("/changePwd")
    public String changePwd(Authentication authentication){
        if(authentication==null||!authentication.isAuthenticated()){
            return "user/login";
        }
        return "/user/changeUserPwd";
    }
}

