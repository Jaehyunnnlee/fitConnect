package fitConnect.service;

import fitConnect.controller.dto.UserRequestDto;
import fitConnect.controller.dto.response.UserResponseDto;
import fitConnect.entity.user.CustomUserDetails;
import fitConnect.entity.user.Role;
import fitConnect.entity.user.User;
import fitConnect.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public UserResponseDto signupAccount(@Valid UserRequestDto requestDto) {
        try {
            String userId = requestDto.getUserId();
            String userName = requestDto.getUserName();
            if (userRepository.existsByUserId(userId)) {
                throw new IllegalArgumentException("이미 사용중인 아이디입니다.");
            } else if (userRepository.existsByUserName(userName)) {
                throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
            } else if (!requestDto.getUserPwd().equals(requestDto.getUserPwdConfirm())) {
                throw new IllegalArgumentException("비밀번호가 서로 일치하지 않습니다.");
            }
            requestDto.setUserPwd(passwordEncoder.encode(requestDto.getUserPwd()));
            User user = new User(requestDto);
            if (userId.equals("admin")) {
                user.setRole(Role.ADMIN);
            } else {
                user.setRole(Role.USER);
            }
            userRepository.save(user);
            return new UserResponseDto(user);
        } catch (IllegalArgumentException ex) {
            // 예외를 잡아서 다시 던져줍니다.
            throw ex;
        }
    }

    @Transactional
    public UserResponseDto changePassword(UserRequestDto userRequestDto,Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        String userId=userInfo.getUsername();
        String userPwd=userRequestDto.getUserPwd();
        String newPassword=userRequestDto.getNewPassword();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new UsernameNotFoundException("로그인하세요"));
        if(passwordEncoder.matches(userPwd,user.getUserPwd())){
            user.setUserPwd(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        }else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return new UserResponseDto(user);
    }

    @Transactional
    public UserResponseDto deleteUser(UserRequestDto userRequestDto,Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        String userId=userInfo.getUsername();
        String userPwd=userRequestDto.getUserPwd();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new UsernameNotFoundException("로그인 하세요"));
        if(passwordEncoder.matches(userPwd,user.getUserPwd())){
            userRepository.delete(user);
        }else{
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return new UserResponseDto(user);
    }
    public UserResponseDto showMyPage(Authentication authentication){
        CustomUserDetails userInfo=(CustomUserDetails) authentication.getPrincipal();
        if(userInfo==null){
            throw new IllegalArgumentException("로그인 하세요");
        }
        String userId=userInfo.getUsername();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new UsernameNotFoundException("로그인 하세요"));
        return new UserResponseDto(user);
    }

}
