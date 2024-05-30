package fitConnect.service;

import fitConnect.controller.dto.response.ExerciseResponseDto;
import fitConnect.repository.ExerciseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    public List<ExerciseResponseDto> getExercise(String exercisePart){
        return exerciseRepository.findAllByExercisePart(exercisePart).stream().map(ExerciseResponseDto::new).toList();
    }

}
