package pl.wsiz.animaltinder.animal.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.wsiz.animaltinder.auth.exception.BusinessException;
import pl.wsiz.animaltinder.auth.exception.ErrorMessage;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InteractionService {

    private final AnimalInteractionRepository animalInteractionRepository;
    private final AnimalService animalService;
    private final PairingRepository pairingRepository;

    @Transactional
    public void likeAnimal(Long animalId, Long animalIdToLike){
        AnimalEntity animal = animalService.getAnimal(animalId);
        AnimalEntity animalToLike = animalService.getAnimal(animalIdToLike);

        AnimalInteractionEntity interactionFromAnimalToLike = animalInteractionRepository.findByInvokerAndReceiver(animalToLike, animal);
        validateIfInteractionAlreadyExist(animal,animalToLike);

        if(interactionFromAnimalToLike != null){
            if(interactionFromAnimalToLike.getLikingStatus().equals(LikingStatus.LIKE)) {
                interactionFromAnimalToLike.setMatchingStatus(MatchingStatus.MATCHED);

                //send to both info bout match
            }
        }else {
            AnimalInteractionEntity animalInteractionEntity = AnimalInteractionEntity.builder()
                    .invoker(animal)
                    .receiver(animalToLike)
                    .likingStatus(LikingStatus.LIKE)
                    .build();
            addPairingHistoryForAnimals(animal,animalToLike);
            animalInteractionRepository.save(animalInteractionEntity);
        }
    }

    @Transactional
    public void dislikeAnimal(Long animalId, Long animalIdToDislike){
        AnimalEntity animal = animalService.getAnimal(animalId);
        AnimalEntity animalToDislike = animalService.getAnimal(animalIdToDislike);

        AnimalInteractionEntity likeThatAlreadyExists = animalInteractionRepository.findByInvokerAndReceiver(animalToDislike, animal);
        validateIfInteractionAlreadyExist(animal,animalToDislike);

        if(likeThatAlreadyExists != null){
            likeThatAlreadyExists.setMatchingStatus(MatchingStatus.UNMATCHED);
        }else{
            AnimalInteractionEntity animalInteractionEntity = AnimalInteractionEntity.builder()
                    .invoker(animal)
                    .receiver(animalToDislike)
                    .likingStatus(LikingStatus.DISLIKE)
                    .matchingStatus(MatchingStatus.UNMATCHED)
                    .build();
            addPairingHistoryForAnimals(animal,animalToDislike);
            animalInteractionRepository.save(animalInteractionEntity);
        }
    }

    private void validateIfInteractionAlreadyExist(AnimalEntity animal, AnimalEntity animalToInteractWith ){
        AnimalInteractionEntity interactionFromInvokingAnimal = animalInteractionRepository.findByInvokerAndReceiver(animal, animalToInteractWith);
        if(interactionFromInvokingAnimal != null){
            throw new BusinessException(ErrorMessage.INTERACTION_ALREADY_EXISTS);
        }
    }

    private void addPairingHistoryForAnimals(AnimalEntity animalOne, AnimalEntity animalTwo){
        addPairingHistoryForAnimal(animalOne, animalTwo.getId());
        addPairingHistoryForAnimal(animalTwo, animalOne.getId());
    }

    private void addPairingHistoryForAnimal(AnimalEntity animal, Long pairedAnimalId){
        List<Long> allPairedIds = pairingRepository.findAllPairedIds(animal.getId());
        if(!allPairedIds.contains(pairedAnimalId)){
            PairingEntity pairingEntity = PairingEntity.builder()
                    .owner(animal)
                    .pairedAnimalId(pairedAnimalId)
                    .build();
            animal.getPairedAnimals().add(pairingEntity);
            pairingRepository.save(pairingEntity);
        }
    }
}
