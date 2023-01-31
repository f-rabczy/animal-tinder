package pl.wsiz.animaltinder.animal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MatchingRepository extends JpaRepository<MatchingEntity,Long > {

    @Query(value = "select p.matchedAnimalId from MatchingEntity p where p.owner = :animal ")
    List<Long> findAllMatchedIds(AnimalEntity animal);

    List<MatchingEntity> findAllByOwner(AnimalEntity owner);

    @Query(value = "select m from MatchingEntity m where m.owner.id = :ownerId and m.chatEntity.id = :chatId")
    Optional<MatchingEntity> findMatchingEntityByOwnerAndChatId(Long ownerId, Long chatId);
}
