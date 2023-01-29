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

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final UserService userService;
    private final AnimalMapper animalMapper;
    private final LikeRepository likeRepository;

    public AnimalDto addAnimal(Long userId, AnimalCreateDto animalCreateDto) {
        UserEntity user = userService.getUser(userId);
        AnimalEntity animalEntity = animalMapper.mapToAnimalEntity(animalCreateDto, user);
        user.getAnimals().add(animalEntity);
        return animalMapper.mapToAnimalDto(animalRepository.save(animalEntity));
    }

    public List<AnimalDto> getAnimalProposition(Long userId, Long animalId) {
        UserEntity user = userService.getUser(userId);
        AnimalEntity animal = getUserAnimal(user, animalId);

        return animalRepository.findAllByCityAndCountyAndCategoryAndUserNot(animal.getCity(), animal.getCounty(), animal.getCategory(), user)
                .stream()
                .map(animalMapper::mapToAnimalDto)
                .toList();
    }

    public void likeAnimal(Long animalId, Long animalIdToLike){
        AnimalEntity animal = getAnimal(animalId);
        AnimalEntity animalToLike = getAnimal(animalIdToLike);

        LikeEntity likeThatAlreadyExists = likeRepository.findByInvokerAndReceiver(animalToLike, animal);
        if(likeThatAlreadyExists != null){
            if(likeThatAlreadyExists.getLikingStatus().equals(LikingStatus.LIKE)){
                likeThatAlreadyExists.setMatchingStatus(MatchingStatus.MATCHED);
                //send to both info bout match
            }else{
                likeThatAlreadyExists.setMatchingStatus(MatchingStatus.UNMATCHED);
            }
        }else{
            LikeEntity likeEntity = LikeEntity.builder()
                    .invoker(animal)
                    .receiver(animalToLike)
                    .likingStatus(LikingStatus.LIKE)
                    .build();
            likeRepository.save(likeEntity);
        }
    }



    public void dislikeAnimal(Long animalId, Long animalIdToDislike){
        AnimalEntity animal = getAnimal(animalId);
        AnimalEntity animalToDislike = getAnimal(animalIdToDislike);

        LikeEntity likeThatAlreadyExists = likeRepository.findByInvokerAndReceiver(animalToDislike, animal);


    }


    private AnimalEntity getAnimal(Long id) {
        return animalRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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
