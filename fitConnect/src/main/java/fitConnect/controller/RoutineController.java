package fitConnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/routines")
public class RoutineController {
    @GetMapping
    public String creatRoutine(){
        return "routine/create-routine-bootstrap";
    }
    @GetMapping("/my-routines")
    public String getRoutines(){
        return "routine/my-routines-bootstrap";
    }
    @GetMapping("/{routineNum}")
    public String getRoutine(@PathVariable("routineNum") Long routineNum, Model model){
        model.addAttribute("routineNum",routineNum);
        return "routine/read-routine-bootstrap";
    }
    @GetMapping("/{routineNum}/edit")
    public String updateRoutine(@PathVariable("routineNum") Long routineNum,Model model){
        model.addAttribute("routineNum",routineNum);
        return "routine/edit-routine-bootstrap";
    }
}
