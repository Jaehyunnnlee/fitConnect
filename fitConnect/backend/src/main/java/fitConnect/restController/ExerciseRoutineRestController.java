package fitConnect.restController;

import fitConnect.dto.ExerciseRoutineRequestDto;
import fitConnect.dto.response.ExerciseRoutineResponseDto;
import fitConnect.service.ExerciseRoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-api/exerciseRoutine")
public class ExerciseRoutineRestController {
    private final ExerciseRoutineService exerciseRoutineService;
    @PostMapping
    public ResponseEntity<?> saveExerciseRoutine(@RequestBody List<ExerciseRoutineRequestDto> requestDto){
        try{
            List<ExerciseRoutineResponseDto> responseDtos=exerciseRoutineService.saveExerciseRoutine(requestDto);
            return ResponseEntity.ok(responseDtos);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{routineNum}")
    public ResponseEntity<?> readExerciseRoutineByRoutineNum(@PathVariable("routineNum") Long routineNum){
        List<ExerciseRoutineResponseDto> responseDtos=exerciseRoutineService.readExerciseRoutine(routineNum);
        return ResponseEntity.ok(responseDtos);
    }

    @PutMapping("/{routineNum}/edit")
    public ResponseEntity<?> editExerciseRoutine(@PathVariable("routineNum") Long routineNum, Authentication authentication,@RequestBody List<ExerciseRoutineRequestDto> requestDtos){
        try {
            List<ExerciseRoutineResponseDto> responseDtos=exerciseRoutineService.updateExercise(routineNum,requestDtos,authentication);
            return ResponseEntity.ok(responseDtos);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{routineNum}/delete")
    public ResponseEntity<?> deleteExerciseRoutine(@PathVariable("routineNum") Long routineNum,@RequestBody List<ExerciseRoutineRequestDto> requestDtos,Authentication authentication){
        try{
            List<ExerciseRoutineResponseDto> responseDtos=exerciseRoutineService.deleteExerciseRoutine(routineNum,requestDtos,authentication);
            return ResponseEntity.ok(responseDtos);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
