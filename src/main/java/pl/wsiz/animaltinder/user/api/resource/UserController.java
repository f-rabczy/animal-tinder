package pl.wsiz.animaltinder.user.api.resource;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.wsiz.animaltinder.user.api.dto.NotificationDto;
import pl.wsiz.animaltinder.user.api.dto.PictureDto;
import pl.wsiz.animaltinder.user.api.dto.UserCreateDto;
import pl.wsiz.animaltinder.user.api.dto.UserDto;
import pl.wsiz.animaltinder.user.domain.FileStorageService;
import pl.wsiz.animaltinder.user.domain.PictureType;
import pl.wsiz.animaltinder.user.domain.UserService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
class UserController {

    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Operation(summary = "Register user")
    @PostMapping
    ResponseEntity<UserDto> createUser(@RequestBody @Valid UserCreateDto request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @Operation(summary = "Get notifications")
    @GetMapping("/{userId}/notifications")
    List<NotificationDto> getNotifications(@PathVariable Long userId) {
        return userService.getUserNotification(userId);
    }

    @Operation(summary = "Delete notification")
    @DeleteMapping("/{userId}/notifications/{notificationId}")
    ResponseEntity<Object> deleteNotification(@PathVariable Long userId, @PathVariable Long notificationId) {
        userService.deleteNotification(userId, notificationId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Save picture")
    @PostMapping("/{userId}/pictures")
    ResponseEntity<PictureDto> saveUserPicture(@PathVariable Long userId, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(fileStorageService.storeFile(file, PictureType.USER, userId));
    }

    @Operation(summary = "Get user picture")
    @GetMapping(value = "/{userId}/pictures",produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] getUserPicture(@PathVariable Long userId) throws IOException {
        return fileStorageService.getPicture(userId,PictureType.USER);
    }

}
