package pl.wsiz.animaltinder.animal.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.animaltinder.animal.api.dto.AnimalInteractionDto;
import pl.wsiz.animaltinder.animal.domain.InteractionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/animals/{animalId}/interactions")
@Slf4j
public class AnimalInteractionController {

    private final InteractionService interactionService;

    @Operation(summary = "Send like to animal")
    @PostMapping("/like")
    public void likeAnimal(@PathVariable Long animalId, @RequestBody AnimalInteractionDto interactionDto){
        interactionService.likeAnimal(animalId, interactionDto.getReceiverAnimalId());
    }

    @Operation(summary = "Send dislike to animal")
    @PostMapping("/dislike")
    public void dislikeAnimal(@PathVariable Long animalId, @RequestBody AnimalInteractionDto interactionDto){
        interactionService.dislikeAnimal(animalId, interactionDto.getReceiverAnimalId());
    }
}
