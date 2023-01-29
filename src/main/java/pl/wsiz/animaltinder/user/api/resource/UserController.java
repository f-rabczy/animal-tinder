package pl.wsiz.animaltinder.user.api.resource;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.animaltinder.auth.exception.BusinessException;
import pl.wsiz.animaltinder.auth.exception.ErrorMessage;
import pl.wsiz.animaltinder.user.api.dto.UserCreateDto;
import pl.wsiz.animaltinder.user.api.dto.UserDto;
import pl.wsiz.animaltinder.user.domain.UserService;

import javax.servlet.http.HttpServletRequest;
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

    @GetMapping
    List<String> get(){
        throw new BusinessException(ErrorMessage.USER_ACCOUNT_SUSPENDED);
    }

}