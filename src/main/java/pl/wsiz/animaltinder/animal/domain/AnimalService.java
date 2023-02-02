package pl.wsiz.animaltinder.animal.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsiz.animaltinder.animal.api.dto.AnimalCreateDto;
import pl.wsiz.animaltinder.animal.api.dto.AnimalDto;
import pl.wsiz.animaltinder.animal.api.dto.MatchingDto;
import pl.wsiz.animaltinder.auth.exception.BusinessException;
import pl.wsiz.animaltinder.auth.exception.ErrorMessage;
import pl.wsiz.animaltinder.user.domain.UserEntity;
import pl.wsiz.animaltinder.user.domain.UserService;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final UserService userService;
    private final AnimalMapper animalMapper;
    private final InteractionRepository interactionRepository;
    private final MatchingRepository matchingRepository;

    public List<AnimalDto> getUserAnimals(Long userId) {
        return animalRepository.findAllByUserId(userId)
                .stream()
                .map(animalMapper::mapToAnimalDto)
                .toList();
    }

    @Transactional
    public AnimalDto addAnimal(Long userId, AnimalCreateDto animalCreateDto) {
        UserEntity user = userService.getUser(userId);
        AnimalEntity animalEntity = animalMapper.mapToAnimalEntity(animalCreateDto);
        animalEntity.setUser(user);
        return animalMapper.mapToAnimalDto(animalRepository.save(animalEntity));
    }

    public void deleteAnimal(Long userId, Long animalId) {
        UserEntity user = userService.getUser(userId);
        AnimalEntity animal = getUserAnimal(user, animalId);
        user.getAnimals().remove(animal);
        animalRepository.delete(animal);
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

    public List<MatchingDto> getAnimalMatching(Long userId, Long animalId) {
        UserEntity user = userService.getUser(userId);
        AnimalEntity animal = getUserAnimal(user, animalId);

        List<MatchingEntity> matchings = matchingRepository.findAllByOwner(animal);
        List<Long> allMatchedIds = extractIds(matchings);
        List<AnimalEntity> matchedAnimals = animalRepository.findAllById(allMatchedIds);

        return mapToMatchingDtoList(matchings, matchedAnimals);
    }

    public AnimalEntity getAnimal(Long id) {
        return animalRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void saveAnimal(AnimalEntity animal) {
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

    private List<Long> extractIds(List<MatchingEntity> animals) {
        return animals.stream()
                .map(MatchingEntity::getMatchedAnimalId)
                .toList();
    }

    private List<MatchingDto> mapToMatchingDtoList(List<MatchingEntity> matchings, List<AnimalEntity> matchedAnimals) {
        List<MatchingDto> result = new ArrayList<>();
        for (MatchingEntity matching : matchings) {
            for (AnimalEntity matchedAnimal : matchedAnimals) {
                if (matching.getMatchedAnimalId().equals(matchedAnimal.getId())) {
                    MatchingDto matchingDto = MatchingDto.builder()
                            .id(matching.getId())
                            .name(matchedAnimal.getName())
                            .category(matchedAnimal.getCategory())
                            .time(matching.getMatchingDate())
                            .chatId(matching.getChatEntity().getId())
                            .build();
                    result.add(matchingDto);
                }
            }
        }
        return result;
    }
}
