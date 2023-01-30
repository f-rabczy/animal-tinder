package pl.wsiz.animaltinder.animal.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsiz.animaltinder.animal.domain.enums.LikingStatus;
import pl.wsiz.animaltinder.animal.domain.enums.MatchingStatus;
import pl.wsiz.animaltinder.auth.exception.BusinessException;
import pl.wsiz.animaltinder.auth.exception.ErrorMessage;
import pl.wsiz.animaltinder.user.domain.NotificationEntity;
import pl.wsiz.animaltinder.user.domain.UserEntity;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static pl.wsiz.animaltinder.user.domain.NotificationEntity.NOTIFICATION_MATCH_PATTERN;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final AnimalInteractionRepository animalInteractionRepository;
    private final AnimalService animalService;
    private final InteractionRepository interactionRepository;
    private final MatchingRepository matchingRepository;

    @Transactional
    public void likeAnimal(Long animalId, Long animalIdToLike) {
        AnimalEntity animal = animalService.getAnimal(animalId);
        AnimalEntity animalToLike = animalService.getAnimal(animalIdToLike);

        AnimalInteractionEntity interactionFromAnimalToLike = animalInteractionRepository.findByInvokerAndReceiver(animalToLike, animal);
        validateIfInteractionAlreadyExist(animal, animalToLike);

        if (interactionFromAnimalToLike != null) {
            if (interactionFromAnimalToLike.getLikingStatus().equals(LikingStatus.LIKE)) {
                interactionFromAnimalToLike.setMatchingStatus(MatchingStatus.MATCHED);
                createMatchingForAnimals(animal,animalToLike);
                createNotifications(animal,animalToLike);

            }
        } else {
            AnimalInteractionEntity animalInteractionEntity = AnimalInteractionEntity.builder()
                    .invoker(animal)
                    .receiver(animalToLike)
                    .likingStatus(LikingStatus.LIKE)
                    .build();
            createPairingHistoryForAnimals(animal, animalToLike);
            animalInteractionRepository.save(animalInteractionEntity);
        }
    }

    @Transactional
    public void dislikeAnimal(Long animalId, Long animalIdToDislike) {
        AnimalEntity animal = animalService.getAnimal(animalId);
        AnimalEntity animalToDislike = animalService.getAnimal(animalIdToDislike);

        AnimalInteractionEntity likeThatAlreadyExists = animalInteractionRepository.findByInvokerAndReceiver(animalToDislike, animal);
        validateIfInteractionAlreadyExist(animal, animalToDislike);

        if (likeThatAlreadyExists != null) {
            likeThatAlreadyExists.setMatchingStatus(MatchingStatus.UNMATCHED);
        } else {
            AnimalInteractionEntity animalInteractionEntity = AnimalInteractionEntity.builder()
                    .invoker(animal)
                    .receiver(animalToDislike)
                    .likingStatus(LikingStatus.DISLIKE)
                    .matchingStatus(MatchingStatus.UNMATCHED)
                    .build();
            createPairingHistoryForAnimals(animal, animalToDislike);
            animalInteractionRepository.save(animalInteractionEntity);
        }
    }

    private void validateIfInteractionAlreadyExist(AnimalEntity animal, AnimalEntity animalToInteractWith) {
        AnimalInteractionEntity interactionFromInvokingAnimal = animalInteractionRepository.findByInvokerAndReceiver(animal, animalToInteractWith);
        if (interactionFromInvokingAnimal != null) {
            throw new BusinessException(ErrorMessage.INTERACTION_ALREADY_EXISTS);
        }
    }

    private void createPairingHistoryForAnimals(AnimalEntity animalOne, AnimalEntity animalTwo) {
        savePairing(animalOne, animalTwo.getId());
        savePairing(animalTwo, animalOne.getId());
    }

    private void savePairing(AnimalEntity animal, Long pairedAnimalId) {
        List<Long> allPairedIds = interactionRepository.findAllPairedIds(animal.getId());
        if (!allPairedIds.contains(pairedAnimalId)) {
            InteractionEntity interactionEntity = InteractionEntity.builder()
                    .owner(animal)
                    .pairedAnimalId(pairedAnimalId)
                    .build();
            animal.getAnimalsInteractionHistory().add(interactionEntity);
            interactionRepository.save(interactionEntity);
        }
    }

    private void createMatchingForAnimals(AnimalEntity animalOne, AnimalEntity animalTwo) {
        saveMatching(animalOne, animalTwo.getId());
        saveMatching(animalTwo, animalOne.getId());
    }

    private void saveMatching(AnimalEntity animal, Long matchedAnimalId) {
        MatchingEntity matching = MatchingEntity.builder()
                .owner(animal)
                .matchedAnimalId(matchedAnimalId)
                .matchingDate(LocalDate.now())
                .build();

        matchingRepository.save(matching);
    }

    private void createNotifications(AnimalEntity animalOne, AnimalEntity animalTwo){
        saveNotification(animalOne);
        saveNotification(animalTwo);
    }

    private void saveNotification(AnimalEntity animal){
        UserEntity user = animal.getUser();
        NotificationEntity notification = NotificationEntity.builder()
                .content(String.format(NOTIFICATION_MATCH_PATTERN, animal.getName()))
                .time(LocalDate.now())
                .user(user)
                .build();
        user.getNotifications().add(notification);
        animalService.saveAnimal(animal);
    }

}
