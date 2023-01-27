package pl.wsiz.animaltinder.auth.util;

public class RequestValidator {

    public static void validateUserRequest(Long id, Long id2){
        if(!id.equals(id2)){
            throw new RuntimeException("You are not permitted to make this request");
        }
    }
}
