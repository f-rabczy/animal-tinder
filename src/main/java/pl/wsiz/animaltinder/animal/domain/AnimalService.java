package pl.wsiz.animaltinder.animal.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsiz.animaltinder.animal.api.dto.AnimalCreateDto;
import pl.wsiz.animaltinder.animal.api.dto.AnimalDto;
import pl.wsiz.animaltinder.auth.exception.BusinessException;
import pl.wsiz.animaltinder.auth.exception.ErrorMessage;
import pl.wsiz.animaltinder.user.domain.UserEntity;
import pl.wsiz.animaltinder.user.domain.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final UserService userService;
    private final AnimalMapper animalMapper;
    private final InteractionRepository interactionRepository;
    private final MatchingRepository matchingRepository;

    public AnimalDto addAnimal(Long userId, AnimalCreateDto animalCreateDto) {
        UserEntity user = userService.getUser(userId);
        AnimalEntity animalEntity = animalMapper.mapToAnimalEntity(animalCreateDto, user);
        user.getAnimals().add(animalEntity);
        return animalMapper.mapToAnimalDto(animalRepository.save(animalEntity));
    }

    public List<AnimalDto> getAnimalProposition(Long userId, Long animalId) {
        UserEntity user = userService.getUser(userId);
        AnimalEntity animal = getUserAnimal(user, animalId);
        List<Long> allPairedAnimalsIds = interactionRepository.findAllPairedIds(animalId);

        return animalRepository.findAllByCityAndCountyAndCategoryAndUserNot(animal.getCity(), animal.getCounty(), animal.getCategory(), user)
                .stream()
                .filter(animal1 -> !allPairedAnimalsIds.contains(animal1.getId()))
                .map(animalMapper::mapToAnimalDto)
                .toList();
    }

    public List<AnimalDto> getAnimalMatching(Long userId, Long animalId){
        UserEntity user = userService.getUser(userId);
        AnimalEntity animal = getUserAnimal(user, animalId);
        List<Long> allMatchedIds = matchingRepository.findAllMatchedIds(animal);

        return animalRepository.findAllById(allMatchedIds)
                .stream()
                .map(animalMapper::mapToAnimalDto)
                .collect(Collectors.toList());
    }

    public AnimalEntity getAnimal(Long id) {
        return animalRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void saveAnimal(AnimalEntity animal){
        animalRepository.save(animal);
    }

    private AnimalEntity getUserAnimal(UserEntity user, Long animalId) {
        List<AnimalEntity> animalEntities = user.getAnimals()
                .stream()
                .filter(animal -> animal.getId().equals(animalId))
                .toList();

        if (animalEntities.isEmpty()) {
            throw new BusinessException(ErrorMessage.REQUEST_NOT_ALLOWED, "You dont have an animal with id " + animalId);
        } else {
            return animalEntities.get(0);
        }
    }

}
