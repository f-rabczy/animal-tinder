package pl.wsiz.animaltinder.animal.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.animaltinder.animal.api.dto.AnimalInteractionDto;
import pl.wsiz.animaltinder.animal.domain.InteractionService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/animal/{animalId}/interaction")
@Slf4j
public class AnimalInteractionController {

    private final InteractionService interactionService;

    @PostMapping("/like")
    public void likeAnimal(@PathVariable Long animalId, @RequestBody AnimalInteractionDto interactionDto){
        interactionService.likeAnimal(animalId, interactionDto.getReceiverAnimalId());
    }

    @PostMapping("/dislike")
    public void dislikeAnimal(@PathVariable Long animalId, @RequestBody AnimalInteractionDto interactionDto){
        interactionService.dislikeAnimal(animalId, interactionDto.getReceiverAnimalId());
    }
}
