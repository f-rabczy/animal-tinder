package pl.wsiz.animaltinder.user.api.resource;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.animaltinder.user.api.dto.NotificationDto;
import pl.wsiz.animaltinder.user.api.dto.UserCreateDto;
import pl.wsiz.animaltinder.user.api.dto.UserDto;
import pl.wsiz.animaltinder.user.domain.UserService;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
class UserController {

    private final UserService userService;

    @PostMapping
    ResponseEntity<UserDto> createUser(@RequestBody @Valid UserCreateDto request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @GetMapping("/{userId}/notifications")
    List<NotificationDto> getNotifications(@PathVariable Long userId){
        return userService.getUserNotification(userId);
    }

    @DeleteMapping("/{userId}/notifications/{notificationId}")
    ResponseEntity<Object> deleteNotification(@PathVariable Long userId, @PathVariable Long notificationId){
        userService.deleteNotification(userId, notificationId);
        return ResponseEntity.ok().build();
    }

}
