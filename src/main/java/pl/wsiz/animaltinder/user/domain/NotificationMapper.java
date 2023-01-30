package pl.wsiz.animaltinder.user.domain;

import org.mapstruct.Mapper;
import pl.wsiz.animaltinder.user.api.dto.NotificationDto;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    NotificationDto mapToNotificationDto(NotificationEntity notificationEntity);
}
