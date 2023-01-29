package pl.wsiz.animaltinder.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    INTERACTION_ALREADY_EXISTS("Interaction with this animal is already made"),
    REQUEST_NOT_ALLOWED("You are not allowed to make this request"),
    USER_ACCOUNT_SUSPENDED("Account is suspended"),
    USER_ACCOUNT_BANNED("Account has been banned");

    private final String message;

}
