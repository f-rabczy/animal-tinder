package pl.wsiz.animaltinder.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class BusinessException extends RuntimeException{

    private final ErrorMessage errorMessage;
    private final HttpStatus httpStatus;
    private final LocalDateTime localDateTime;
    private final String description;

    public BusinessException(ErrorMessage errorMessage, HttpStatus httpStatus,  String description) {
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
        this.localDateTime = LocalDateTime.now();
        this.description = description;
    }

    public BusinessException(ErrorMessage errorMessage) {
        this(errorMessage,HttpStatus.BAD_REQUEST,"");
    }

    public BusinessException(ErrorMessage errorMessage, String description){
        this(errorMessage,HttpStatus.BAD_REQUEST,description);
    }

}
