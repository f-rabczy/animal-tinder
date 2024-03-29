package pl.wsiz.animaltinder.auth.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {

    NO_PICTURE_AVAILABLE("Picture for given user or animal doesnt exist"),
    ANIMAL_CHAT_DOES_NOT_EXIST("Animal with given id does not have a chat with this id"),
    NOTIFICATION_DOES_NOT_EXIST("User with given id, does not have this notification"),
    INTERACTION_ALREADY_EXISTS("Interaction with this animal is already made"),
    REQUEST_NOT_ALLOWED("You are not allowed to make this request"),
    USER_ACCOUNT_SUSPENDED("Account is suspended"),
    USER_ACCOUNT_BANNED("Account has been banned");

    private final String message;

}
