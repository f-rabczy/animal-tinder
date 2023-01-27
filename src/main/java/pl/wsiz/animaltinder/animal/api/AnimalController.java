package pl.wsiz.animaltinder.animal.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.wsiz.animaltinder.animal.api.dto.AnimalCreateDto;
import pl.wsiz.animaltinder.animal.api.dto.AnimalDto;

import static pl.wsiz.animaltinder.auth.util.RequestValidator.validateUserRequest;
import static pl.wsiz.animaltinder.auth.util.UserDetailsUtil.getCurrentUserId;

@RestController
public class AnimalController {

//    @PostMapping("/user/{userId}/animal")
//    ResponseEntity<AnimalDto> addAnimalToUser(@PathVariable Long userId, @RequestBody AnimalCreateDto animalCreateDto){
//        validateUserRequest(getCurrentUserId(),userId);
//
//    }
}
