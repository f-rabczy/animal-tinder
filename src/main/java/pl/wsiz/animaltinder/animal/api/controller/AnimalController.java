package pl.wsiz.animaltinder.animal.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsiz.animaltinder.animal.api.dto.AnimalCreateDto;
import pl.wsiz.animaltinder.animal.api.dto.AnimalDto;
import pl.wsiz.animaltinder.animal.api.dto.MatchingDto;
import pl.wsiz.animaltinder.animal.domain.AnimalService;

import javax.validation.Valid;

import java.util.List;

import static pl.wsiz.animaltinder.auth.util.RequestValidator.validateUserRequest;
import static pl.wsiz.animaltinder.auth.util.UserDetailsUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @GetMapping("/user/{userId}/animal")
    List<AnimalDto> getUserAnimals(@PathVariable Long userId){
        return animalService.getUserAnimals(userId);
    }

    @Operation(summary = "Add animal to user")
    @PostMapping("/user/{userId}/animal")
    ResponseEntity<AnimalDto> addAnimalToUser(@PathVariable Long userId, @RequestBody @Valid AnimalCreateDto animalCreateDto) {
        validateUserRequest(getCurrentUserId(), userId);
        return ResponseEntity.ok(animalService.addAnimal(userId, animalCreateDto));
    }

    @Operation(summary = "Find propositions for animal")
    @GetMapping("/user/{userId}/animal/{animalId}")
    List<AnimalDto> getAnimalProposition(@PathVariable Long userId,@PathVariable Long animalId){
        validateUserRequest(getCurrentUserId(),userId);
        return animalService.getAnimalProposition(userId,animalId);
    }

    @Operation(summary = "Get matched animals")
    @GetMapping("/user/{userId}/animal/{animalId}/matchings")
    List<MatchingDto> getAnimalMatches(@PathVariable Long userId, @PathVariable Long animalId){
        validateUserRequest(getCurrentUserId(),userId);
        return animalService.getAnimalMatching(userId, animalId);
    }

}
