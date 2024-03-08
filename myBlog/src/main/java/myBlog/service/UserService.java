package myBlog.service;

import lombok.RequiredArgsConstructor;
import myBlog.controller.dto.UserRequestDto;
import myBlog.controller.dto.response.UserResponseDto;
import myBlog.entity.user.CustomUserDetails;
import myBlog.entity.user.Role;
import myBlog.entity.user.User;
import myBlog.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDto signupAccount(UserRequestDto requestDto){
        String userId=requestDto.getUserId();
        if(userRepository.existsByUserId(userId)){
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        else if(!requestDto.getUserPwd().equals(requestDto.getUserPwdConfirm())){
            throw new IllegalArgumentException("비밀번호가 서로 일치하지 않습니다.");
        }
        requestDto.setUserPwd(passwordEncoder.encode(requestDto.getUserPwd()));
        User user=new User(requestDto);
        if(userId.equals("admin")){
            user.setRole(Role.ADMIN);
        }
        else{
            user.setRole(Role.USER);
        }
        userRepository.save(user);
        return new UserResponseDto(user);
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


}
