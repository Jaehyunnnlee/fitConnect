package fitConnect.service;

import fitConnect.controller.dto.ExerciseRoutineRequestDto;
import fitConnect.controller.dto.response.ExerciseRoutineResponseDto;
import fitConnect.entity.Exercise;
import fitConnect.entity.Routine;
import fitConnect.entity.exerciseRoutine.ExerciseRoutine;
import fitConnect.config.security.CustomUserDetails;
import fitConnect.repository.ExerciseRepository;
import fitConnect.repository.ExerciseRoutineRepository;
import fitConnect.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseRoutineService {
    private final ExerciseRoutineRepository exerciseRoutineRepository;
    private final RoutineRepository routineRepository;
    private final ExerciseRepository exerciseRepository;

    public List<ExerciseRoutineResponseDto> saveExerciseRoutine(@RequestBody List<ExerciseRoutineRequestDto> routineData) {
        List<ExerciseRoutineResponseDto> responseDtos = new ArrayList<>();
        for (ExerciseRoutineRequestDto requestDto : routineData) {
            Routine routine = routineRepository.findByRoutineNum(requestDto.getRoutineNum())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid routine number: " + requestDto.getRoutineNum()));
            Exercise exercise = exerciseRepository.findByExerciseNum(requestDto.getExerciseNum())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid exercise number: " + requestDto.getExerciseNum()));
            requestDto.setRoutineAndExercise(routine, exercise);
            requestDto.setWeight(requestDto.getWeight());
            requestDto.setSets(requestDto.getSets());
            requestDto.setReps(requestDto.getReps());

            ExerciseRoutine exerciseRoutine = new ExerciseRoutine(requestDto);
            exerciseRoutineRepository.save(exerciseRoutine);
            responseDtos.add(new ExerciseRoutineResponseDto(exerciseRoutine));
        }
        return responseDtos;
    }

    public List<ExerciseRoutineResponseDto> readExerciseRoutine(@PathVariable Long routineNum) {
        List<ExerciseRoutineResponseDto> exerciseRoutineResponseDtoList = exerciseRoutineRepository.findByExerciseRoutinePKRoutineRoutineNum(routineNum).stream()
                .map(ExerciseRoutineResponseDto::new).toList();
        return exerciseRoutineResponseDtoList;
    }

    public List<ExerciseRoutineResponseDto> updateExercise(Long routineNum, List<ExerciseRoutineRequestDto> requestDtos, Authentication authentication) {
        CustomUserDetails userInfo = (CustomUserDetails) authentication.getPrincipal();
        List<ExerciseRoutine> exerciseRoutines = exerciseRoutineRepository.findByExerciseRoutinePKRoutineRoutineNum(routineNum);

        for (ExerciseRoutineRequestDto requestDto : requestDtos) {
            boolean exist = false;
            for (ExerciseRoutine exerciseRoutine : exerciseRoutines) {
                if (exerciseRoutine.getExerciseRoutinePK().getRoutine().getRoutineNum() == requestDto.getRoutineNum()) {
                    if (exerciseRoutine.getExerciseRoutinePK().getExercise().getExerciseNum() == requestDto.getExerciseNum()) {
                        exist=true;
                        exerciseRoutine.setReps(requestDto.getReps());
                        exerciseRoutine.setWeight(requestDto.getWeight());
                        exerciseRoutine.setSets(requestDto.getSets());
                        if (!userInfo.getUsername().equals(exerciseRoutine.getExerciseRoutinePK().getRoutine().getUser().getUserId())) {
                            throw new IllegalArgumentException("현재 유저는 삭제할 권한이 없습니다.");
                        }
                        exerciseRoutineRepository.save(exerciseRoutine);
                        break;
                    }
                }
            }
            if(!exist){
                Routine routine = routineRepository.findByRoutineNum(requestDto.getRoutineNum())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid routine number: " + requestDto.getRoutineNum()));
                Exercise exercise = exerciseRepository.findByExerciseNum(requestDto.getExerciseNum())
                        .orElseThrow(() -> new IllegalArgumentException("Invalid exercise number: " + requestDto.getExerciseNum()));
                requestDto.setRoutineAndExercise(routine, exercise);
                requestDto.setWeight(requestDto.getWeight());
                requestDto.setSets(requestDto.getSets());
                requestDto.setReps(requestDto.getReps());

                ExerciseRoutine newExerciseRoutine = new ExerciseRoutine(requestDto);
                if (!userInfo.getUsername().equals(newExerciseRoutine.getExerciseRoutinePK().getRoutine().getUser().getUserId())) {
                    throw new IllegalArgumentException("현재 유저는 삭제할 권한이 없습니다.");
                }
                exerciseRoutineRepository.save(newExerciseRoutine);
                exerciseRoutines.add(newExerciseRoutine);
            }
        }
        return exerciseRoutines.stream().map(ExerciseRoutineResponseDto::new).toList();
    }

    public List<ExerciseRoutineResponseDto> deleteExerciseRoutine(Long routineNum, @RequestBody List<ExerciseRoutineRequestDto> requestDtos, Authentication authentication) {
        CustomUserDetails userInfo = (CustomUserDetails) authentication.getPrincipal();
        List<ExerciseRoutine> exerciseRoutines = exerciseRoutineRepository.findByExerciseRoutinePKRoutineRoutineNum(routineNum);

        for (ExerciseRoutineRequestDto requestDto : requestDtos) {
            Iterator<ExerciseRoutine> iterator = exerciseRoutines.iterator();
            while (iterator.hasNext()) {
                ExerciseRoutine exerciseRoutine = iterator.next();
                if (exerciseRoutine.getExerciseRoutinePK().getRoutine().getRoutineNum() == routineNum) {
                    if (exerciseRoutine.getExerciseRoutinePK().getExercise().getExerciseNum() == requestDto.getExerciseNum()) {
                        if (!userInfo.getUsername().equals(exerciseRoutine.getExerciseRoutinePK().getRoutine().getUser().getUserId())) {
                            throw new IllegalArgumentException("현재 유저는 삭제할 권한이 없습니다.");
                        }
                        iterator.remove(); // 안전하게 요소를 제거
                        exerciseRoutineRepository.delete(exerciseRoutine);
                    }
                }
            }
        }
        return exerciseRoutines.stream().map(ExerciseRoutineResponseDto::new).toList();
    }

}
