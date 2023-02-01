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
                saveMatchingForAnimals(animal, animalToLike);
                saveNotifications(animal, animalToLike);

            }
        } else {
            AnimalInteractionEntity animalInteractionEntity = AnimalInteractionEntity.builder()
                    .invoker(animal)
                    .receiver(animalToLike)
                    .likingStatus(LikingStatus.LIKE)
                    .build();
            savePairingHistory(animal, animalToLike.getId());
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
            savePairingHistory(animal, animalToDislike.getId());
            animalInteractionRepository.save(animalInteractionEntity);
        }
    }

    private void validateIfInteractionAlreadyExist(AnimalEntity animal, AnimalEntity animalToInteractWith) {
        AnimalInteractionEntity interactionFromInvokingAnimal = animalInteractionRepository.findByInvokerAndReceiver(animal, animalToInteractWith);
        if (interactionFromInvokingAnimal != null) {
            throw new BusinessException(ErrorMessage.INTERACTION_ALREADY_EXISTS);
        }
    }

    private void savePairingHistory(AnimalEntity animal, Long pairedAnimalId) {
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

    private void saveMatchingForAnimals(AnimalEntity animalOne, AnimalEntity animalTwo) {
        ChatEntity chat = createChat(animalOne, animalTwo);
        saveMatching(animalOne, animalTwo.getId(), chat);
        saveMatching(animalTwo, animalOne.getId(), chat);
    }

    private void saveMatching(AnimalEntity animal, Long matchedAnimalId, ChatEntity chatEntity) {
        MatchingEntity matching = MatchingEntity.builder()
                .owner(animal)
                .matchedAnimalId(matchedAnimalId)
                .matchingDate(LocalDate.now())
                .chatEntity(chatEntity)
                .build();
        matchingRepository.save(matching);
    }

    private ChatEntity createChat(AnimalEntity animalOne, AnimalEntity animalTwo) {
        return ChatEntity.builder()
                .animalOne(animalOne)
                .animalTwo(animalTwo)
                .build();
    }

    private void saveNotifications(AnimalEntity animalOne, AnimalEntity animalTwo) {
        saveNotification(animalOne);
        saveNotification(animalTwo);
    }

    private void saveNotification(AnimalEntity animal) {
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
