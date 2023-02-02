package pl.wsiz.animaltinder.animal.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.animaltinder.animal.api.dto.MessageCreateDto;
import pl.wsiz.animaltinder.animal.api.dto.MessageDto;
import pl.wsiz.animaltinder.animal.domain.ChatService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "Send message")
    @PostMapping("/animals/{animalId}/chat/{chatId}")
    public void sendChatMessage(@PathVariable Long animalId, @PathVariable Long chatId, @RequestBody MessageCreateDto messageCreateDto){
        chatService.saveMessage(animalId, chatId, messageCreateDto);
    }

    @Operation(summary = "Get messages")
    @GetMapping("/animals/{animalId}/chat/{chatId}")
    public List<MessageDto> getChatMessages(@PathVariable Long animalId, @PathVariable Long chatId){
        return chatService.getMessages(animalId, chatId);
    }
}
