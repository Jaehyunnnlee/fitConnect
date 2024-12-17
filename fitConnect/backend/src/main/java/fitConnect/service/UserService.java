package fitConnect.service;

import fitConnect.config.security.JwtProvider;
import fitConnect.config.security.UserPrincipal;
import fitConnect.controller.dto.UserRequestDto;
import fitConnect.controller.dto.response.AccessTokenResponseDto;
import fitConnect.controller.dto.response.UserResponseDto;
import fitConnect.entity.user.Role;
import fitConnect.entity.user.User;
import fitConnect.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
@Slf4j
public class UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwTProvider;


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
    public AccessTokenResponseDto login(UserRequestDto dto){
//        log.info(dto.getUserId());
        User user=userRepository.findByUserId(dto.getUserId()).orElseThrow(()->new UsernameNotFoundException("아이디가 존재하지 않습니다."));
        if(!passwordEncoder.matches(dto.getUserPwd(),user.getUserPwd())){
            throw new BadCredentialsException("비밀번호가 틀렸습니다.");
        }
        UserResponseDto info=new UserResponseDto(user);
        String acessToken= jwTProvider.createToken(info);
        return new AccessTokenResponseDto(acessToken);
    }
    @Transactional
    public UserResponseDto changePassword(UserRequestDto userRequestDto,Authentication authentication){
        UserPrincipal userInfo=(UserPrincipal) authentication.getPrincipal();
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
        UserPrincipal userInfo=(UserPrincipal) authentication.getPrincipal();
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
        UserPrincipal userInfo=(UserPrincipal) authentication.getPrincipal();
        if(userInfo==null){
            throw new IllegalArgumentException("로그인 하세요");
        }
        String userId=userInfo.getUsername();
        User user=userRepository.findByUserId(userId).orElseThrow(()->new UsernameNotFoundException("로그인 하세요"));
        return new UserResponseDto(user);
    }

}
