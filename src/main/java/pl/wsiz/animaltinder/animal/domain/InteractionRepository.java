package pl.wsiz.animaltinder.animal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InteractionRepository extends JpaRepository<InteractionEntity, Long> {

    @Query(value = "select p.pairedAnimalId from InteractionEntity p where p.owner.id = :animalId ")
    List<Long> findAllPairedIds(Long animalId);
}
