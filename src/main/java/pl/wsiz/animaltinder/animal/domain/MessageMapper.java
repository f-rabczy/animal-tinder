package pl.wsiz.animaltinder.animal.domain;

import org.mapstruct.Mapper;
import pl.wsiz.animaltinder.animal.api.dto.MessageDto;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageDto mapToMessageDto(MessageEntity messageEntity);
}
