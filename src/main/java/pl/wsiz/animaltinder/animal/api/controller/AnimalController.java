package pl.wsiz.animaltinder.animal.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.wsiz.animaltinder.animal.api.dto.AnimalCreateDto;
import pl.wsiz.animaltinder.animal.api.dto.AnimalDto;
import pl.wsiz.animaltinder.animal.api.dto.MatchingDto;
import pl.wsiz.animaltinder.animal.domain.AnimalService;
import pl.wsiz.animaltinder.user.api.dto.PictureDto;
import pl.wsiz.animaltinder.user.domain.FileStorageService;
import pl.wsiz.animaltinder.user.domain.PictureType;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;

import static pl.wsiz.animaltinder.auth.util.RequestValidator.validateUserRequest;
import static pl.wsiz.animaltinder.auth.util.UserDetailsUtil.getCurrentUserId;

@RestController
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;
    private final FileStorageService fileStorageService;

    @Operation(summary = "Get user's animals")
    @GetMapping("/users/{userId}/animals")
    List<AnimalDto> getUserAnimals(@PathVariable Long userId) {
        return animalService.getUserAnimals(userId);
    }

    @Operation(summary = "Add animal to user")
    @PostMapping("/users/{userId}/animals")
    public ResponseEntity<AnimalDto> addAnimalToUser(@PathVariable Long userId, @RequestBody @Valid AnimalCreateDto animalCreateDto) {
//        validateUserRequest(getCurrentUserId(), userId);
        return ResponseEntity.ok(animalService.addAnimal(userId, animalCreateDto));
    }

    @Operation(summary = "Delete animal")
    @DeleteMapping("/users/{userId}/animals/{animalId}")
    ResponseEntity<Object> deleteAnimal(@PathVariable Long userId, @PathVariable Long animalId) {
        animalService.deleteAnimal(userId, animalId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Find propositions for animal")
    @GetMapping("/users/{userId}/animals/{animalId}")
    List<AnimalDto> getAnimalProposition(@PathVariable Long userId, @PathVariable Long animalId) {
//        validateUserRequest(getCurrentUserId(), userId);
        return animalService.getAnimalProposition(userId, animalId);
    }

    @Operation(summary = "Get matched animals")
    @GetMapping("/user/{userId}/animals/{animalId}/matchings")
    List<MatchingDto> getAnimalMatches(@PathVariable Long userId, @PathVariable Long animalId) {
//        validateUserRequest(getCurrentUserId(), userId);
        return animalService.getAnimalMatching(userId, animalId);
    }

    @Operation(summary = "Save animal picture")
    @PostMapping("/users/{userId}/animals/{animalId}/pictures")
    ResponseEntity<PictureDto> saveUserPicture(@PathVariable Long animalId, @RequestParam MultipartFile file) {
        return ResponseEntity.ok(fileStorageService.storeFile(file, PictureType.ANIMAL, animalId));
    }

    @Operation(summary = "Get animal picture")
    @GetMapping(value = "/users/{userId}/animals/{animalId}/pictures", produces = MediaType.IMAGE_JPEG_VALUE)
    byte[] getUserPicture(@PathVariable Long userId, @PathVariable Long animalId) throws IOException {
        return fileStorageService.getPicture(animalId, PictureType.ANIMAL);
    }

}
