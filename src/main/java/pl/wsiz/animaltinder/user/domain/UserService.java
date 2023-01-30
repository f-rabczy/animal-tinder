package pl.wsiz.animaltinder.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsiz.animaltinder.auth.exception.BusinessException;
import pl.wsiz.animaltinder.auth.exception.ErrorMessage;
import pl.wsiz.animaltinder.user.api.dto.NotificationDto;
import pl.wsiz.animaltinder.user.api.dto.UserCreateDto;
import pl.wsiz.animaltinder.user.api.dto.UserDto;
import pl.wsiz.animaltinder.user.mapper.UserMapper;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserMapper userMapper;
    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public Optional<UserEntity> getOptionalOfUserByUsername(String username) {
        return userRepository.findUserWithRolesByUsername(username);
    }

    public UserDto createUser(UserCreateDto request) {
        validateUserCreateRequest(request);
        UserEntity userEntity = userMapper.mapUserCreateRequestToUserEntity(request);
        grantRoles(userEntity, Role.USER);
        UserEntity save = userRepository.save(userEntity);
        return userMapper.mapUserEntityToUserDTO(save);
    }

    public UserEntity getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<NotificationDto> getUserNotification(Long userId) {
        return notificationRepository.findAllByUserId(userId)
                .stream()
                .map(notificationMapper::mapToNotificationDto)
                .toList();
    }

    private void grantRoles(UserEntity userEntity, Role... roles) {
        Arrays.stream(roles)
                .map(this::fetchOrCreateUserRoleEntity)
                .forEach(userEntity::addRole);
    }

    private UserRoleEntity fetchOrCreateUserRoleEntity(Role role) {
        UserRoleEntity userRoleEntity = userRoleRepository.findByName(role);
        return userRoleEntity != null ?
                userRoleEntity :
                userMapper.mapRoleEnumToUserRoleEntity(role);
    }

    private void validateUserCreateRequest(UserCreateDto request) {
        if (userRepository.findByUsername(request.getUsername()) != null) {
            throw new IllegalArgumentException("Username is taken");
        }

        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("Account with given email already exists");
        }
    }

    public void deleteNotification(Long userId, Long notificationId) {
        notificationRepository.findNotificationByIdAndUserId(notificationId, userId)
                .ifPresentOrElse(notificationRepository::delete, () -> {
                    throw new BusinessException(ErrorMessage.NOTIFICATION_DOES_NOT_EXIST);
                });
    }
}
