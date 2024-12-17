package fitConnect.restController;


import fitConnect.controller.dto.RoutineRequestDto;
import fitConnect.controller.dto.response.RoutineResponseDto;
import fitConnect.config.security.CustomUserDetails;
import fitConnect.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-api/routines")
public class RoutineRestController {
    private final RoutineService routineService;

    @PostMapping
    public ResponseEntity<?> writeRoutine(@RequestBody RoutineRequestDto requestDto, Authentication authentication){
        try {
            RoutineResponseDto routine=routineService.writeRoutine(requestDto, authentication);
            return ResponseEntity.ok(routine);
        } catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/my-routines")
    public ResponseEntity<?> readMyRoutines(Authentication authentication){
        try{
            CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
            String userId=userInfo.getUsername();
            List<RoutineResponseDto> routineRequestDtoList=routineService.getMyRoutines(userId);
            return ResponseEntity.ok(routineRequestDtoList);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{routineNum}")
    public ResponseEntity<?> readRoutineByRoutineNum(@PathVariable("routineNum") Long routineNum){
        try{
            RoutineResponseDto responseDto=routineService.getRoutine(routineNum);
            return ResponseEntity.ok(responseDto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PutMapping("/{routineNum}/edit")
    public ResponseEntity<?> updateRoutine(@PathVariable("routineNum") Long routineNum,@RequestBody RoutineRequestDto requestDto,Authentication authentication){
        try{
            RoutineResponseDto responseDto=routineService.updateRoutine(routineNum,requestDto,authentication);
            return ResponseEntity.ok(responseDto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{routineNum}/delete")
    public ResponseEntity<?> deleteRoutine(@PathVariable("routineNum") Long routineNum,Authentication authentication){
        try {
            RoutineResponseDto responseDto=routineService.deleteRoutine(routineNum,authentication);
            return ResponseEntity.ok(responseDto);
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{routineNum}/complete")
    public ResponseEntity<?> markRoutineAsCompleted(@PathVariable("routineNum") Long routineNum, @RequestParam("completed") boolean completed) {
        System.out.println(completed);
        try{
            routineService.updateRoutineCompletionStatus(routineNum, completed);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
