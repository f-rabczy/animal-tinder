package pl.wsiz.animaltinder.auth.util;

import pl.wsiz.animaltinder.auth.exception.BusinessException;
import pl.wsiz.animaltinder.auth.exception.ErrorMessage;

public class RequestValidator {

    public static void validateUserRequest(Long id, Long id2){
        if(!id.equals(id2)){
            throw new BusinessException(ErrorMessage.REQUEST_NOT_ALLOWED);
        }
    }
}
