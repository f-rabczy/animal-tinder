package pl.wsiz.animaltinder.animal.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsiz.animaltinder.animal.api.dto.MessageCreateDto;
import pl.wsiz.animaltinder.animal.api.dto.MessageDto;
import pl.wsiz.animaltinder.auth.exception.BusinessException;
import pl.wsiz.animaltinder.auth.exception.ErrorMessage;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MatchingRepository matchingRepository;
    private final MessageMapper messageMapper;

    public void saveMessage(Long animalId, Long chatId, MessageCreateDto messageCreateDto){
        MatchingEntity matching = getMatching(animalId, chatId);
        ChatEntity chat = matching.getChatEntity();

        MessageEntity message = MessageEntity.builder()
                .content(messageCreateDto.getContent())
                .senderId(animalId)
                .time(LocalDateTime.now())
                .build();

        chat.getMessages().add(message);
        chatRepository.save(chat);
    }

    public List<MessageDto> getMessages(Long animalId, Long chatId){
        ChatEntity chat = chatRepository.findChatEntityByIdAndParticipantId(chatId, animalId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.ANIMAL_CHAT_DOES_NOT_EXIST));
        return chat.getMessages()
                .stream()
                .sorted(localDateTimeComparator)
                .map(messageMapper::mapToMessageDto)
                .toList();
    }

    private MatchingEntity getMatching(Long animalId, Long chatId){
        return matchingRepository.findMatchingEntityByOwnerAndChatId(animalId, chatId)
                .orElseThrow(() -> new BusinessException(ErrorMessage.ANIMAL_CHAT_DOES_NOT_EXIST));
    }

    Comparator<MessageEntity> localDateTimeComparator = Comparator.comparing(MessageEntity::getTime);

}
