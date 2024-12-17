package fitConnect.restController;

import fitConnect.dto.response.ExerciseResponseDto;
import fitConnect.service.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ExerciseRestController {
    private final ExerciseService exerciseService;
    @GetMapping("/rest-api/exercises/{exercisePart}")
    public ResponseEntity<?> readExercises(@PathVariable("exercisePart") String exercisePart){
        List<ExerciseResponseDto> exerciseList=exerciseService.getExercise(exercisePart);
        return ResponseEntity.ok(exerciseList);
    }
}
