package pl.wsiz.animaltinder.animal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatEntity,Long> {

    @Query(value = "select ch from ChatEntity ch where ch.id = :id and (ch.animalOne.id = :animalId or ch.animalTwo.id = :animalId)")
    Optional<ChatEntity> findChatEntityByIdAndParticipantId(Long id, Long animalId);
}
