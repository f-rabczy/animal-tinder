package pl.wsiz.animaltinder.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {


    REQUEST_NOT_ALLOWED("You are not allowed to make this request"),
    USER_ACCOUNT_SUSPENDED("Account is suspended"),
    USER_ACCOUNT_BANNED("Account has been banned");

    private final String message;

}
