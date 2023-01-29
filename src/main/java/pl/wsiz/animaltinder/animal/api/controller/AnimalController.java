package pl.wsiz.animaltinder.animal.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.animaltinder.animal.api.dto.AnimalCreateDto;
import pl.wsiz.animaltinder.animal.api.dto.AnimalDto;
import pl.wsiz.animaltinder.animal.domain.AnimalService;

import javax.validation.Valid;

import java.util.List;

import static pl.wsiz.animaltinder.auth.util.RequestValidator.validateUserRequest;
import static pl.wsiz.animaltinder.auth.util.UserDetailsUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @PostMapping("/user/{userId}/animal")
    ResponseEntity<AnimalDto> addAnimalToUser(@PathVariable Long userId, @RequestBody @Valid AnimalCreateDto animalCreateDto) {
        validateUserRequest(getCurrentUserId(), userId);
        return ResponseEntity.ok(animalService.addAnimal(userId, animalCreateDto));
    }

    @GetMapping("/user/{userId}/animal/{animalId}")
    List<AnimalDto> getAnimalProposition(@PathVariable Long userId,@PathVariable Long animalId){
        validateUserRequest(getCurrentUserId(),userId);
        return animalService.getAnimalProposition(userId,animalId);
    }

}
