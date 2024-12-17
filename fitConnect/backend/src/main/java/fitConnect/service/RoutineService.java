package fitConnect.service;

import fitConnect.controller.dto.RoutineRequestDto;
import fitConnect.controller.dto.response.RoutineResponseDto;
import fitConnect.entity.Routine;
import fitConnect.config.security.CustomUserDetails;
import fitConnect.entity.user.User;
import fitConnect.repository.RoutineRepository;
import fitConnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineService {
    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;

    @Transactional
    public RoutineResponseDto writeRoutine(RoutineRequestDto requestDto, Authentication authentication){
        CustomUserDetails userInfo = (CustomUserDetails) authentication.getPrincipal();
        String userId = userInfo.getUsername();
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("로그인 해주세요"));
        requestDto.setUser(user);
        requestDto.setCompleted(false);
        Routine routine = new Routine(requestDto);
        routineRepository.save(routine);
        return new RoutineResponseDto(routine);
    }
    public List<RoutineResponseDto> getMyRoutines(String userId){
        return routineRepository.findAllByUserUserIdOrderByModifiedAtDesc(userId).stream().map(RoutineResponseDto::new).toList();
    }

    public RoutineResponseDto getRoutine(Long routineNum){
        return routineRepository.findByRoutineNum(routineNum).map(RoutineResponseDto::new).orElseThrow(()->new IllegalArgumentException("루틴이 존재하지 않습니다."));
    }

    public RoutineResponseDto updateRoutine(Long routineNum,RoutineRequestDto requestDto,Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        Routine routine=routineRepository.findByRoutineNum(routineNum).orElseThrow(()->new IllegalArgumentException("루틴이 존재하지 않습니다."));
        routine.setRoutineTitle(requestDto.getRoutineTitle());
        routine.setRoutineDescription(requestDto.getRoutineDescription());
        if(!userInfo.getUsername().equals(routine.getUser().getUserId())){
            throw new IllegalArgumentException("현재 유저는 수정할 권한이 없습니다.");
        }
        routineRepository.save(routine);
        return new RoutineResponseDto(routine);
    }

    public RoutineResponseDto deleteRoutine(Long routineNum,Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        Routine routine=routineRepository.findByRoutineNum(routineNum).orElseThrow(()->new IllegalArgumentException("루틴이 존재하지 않습니다."));
        if(!routine.getUser().getUserId().equals(userInfo.getUsername())){
            throw new IllegalArgumentException("현재 유저는 삭제할 권한이 없습니다.");
        }
        routineRepository.delete(routine);
        return new RoutineResponseDto(routine);
    }

    public void updateRoutineCompletionStatus(Long routineNum, @RequestParam("completed") boolean completed) {
        System.out.println(completed);
        Routine routine = routineRepository.findById(routineNum)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 루틴입니다."));
        routine.setCompleted(completed);
        routineRepository.save(routine);
    }

}
