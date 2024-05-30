package fitConnect.error;

import fitConnect.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        String errorMessage = ex.getMessage();

        if (errorMessage.contains("아이디")) {
            fieldErrors.put("userId", errorMessage);
        } else if (errorMessage.contains("닉네임")) {
            fieldErrors.put("userName", errorMessage);
        } else if (errorMessage.contains("비밀번호")) {
            fieldErrors.put("userPwd", errorMessage);
        }

        ErrorResponse response = new ErrorResponse(fieldErrors);
        System.out.println("fieldErrors: " + fieldErrors);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e){
        Map<String,String> fieldErrors=e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));

        ErrorResponse response=new ErrorResponse(fieldErrors);
        return ResponseEntity.badRequest().body(response);
    }
}
