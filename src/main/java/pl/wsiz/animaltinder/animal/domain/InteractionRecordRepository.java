package pl.wsiz.animaltinder.animal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InteractionRecordRepository extends JpaRepository<InteractionRecordEntity, Long> {

    @Query(value = "select p.pairedAnimalId from InteractionRecordEntity p where p.owner.id = :animalId ")
    List<Long> findAllPairedIds(Long animalId);
}
