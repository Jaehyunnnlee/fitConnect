package fitConnect.error;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ErrorResponse {
    private Map<String,String> fieldErrors;

    public ErrorResponse(Map<String,String> fieldErrors){
        this.fieldErrors=fieldErrors;
    }
}
