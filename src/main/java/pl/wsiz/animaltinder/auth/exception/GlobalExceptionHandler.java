package pl.wsiz.animaltinder.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleException(BusinessException businessException){
        Map<String, String> responseBody = Map.of(
                "errorMessage", businessException.getErrorMessage().getMessage(),
                "time", businessException.getLocalDateTime().toString(),
                "description", businessException.getDescription(),
                "httpStatus", businessException.getHttpStatus().toString()
        );
        return new ResponseEntity<>(responseBody,businessException.getHttpStatus());
    }
}
