package fitConnect.restController;


import lombok.RequiredArgsConstructor;
import fitConnect.controller.dto.UserRequestDto;
import fitConnect.controller.dto.response.UserResponseDto;
import fitConnect.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest-api/users")
public class UserRestController {
    private final UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<?> signupAccount(@RequestBody UserRequestDto requestDto) {
        try {
            UserResponseDto responseDto = userService.signupAccount(requestDto);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/myPage")
    public ResponseEntity<?> myPage(Authentication authentication) {
        try {
            UserResponseDto responseDto = userService.showMyPage(authentication);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/passwords/{userId}")
    public ResponseEntity<?> changePassword(@RequestBody UserRequestDto userRequestDto,Authentication authentication){
        try{
            UserResponseDto responseDto=userService.changePassword(userRequestDto,authentication);
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@RequestBody UserRequestDto userRequestDto,Authentication authentication){
        try{
            UserResponseDto responseDto=userService.deleteUser(userRequestDto,authentication);
            return ResponseEntity.ok(responseDto);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
