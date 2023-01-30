package pl.wsiz.animaltinder.animal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchingRepository extends JpaRepository<MatchingEntity,Long > {

    @Query(value = "select p.matchedAnimalId from MatchingEntity p where p.owner = :animal ")
    List<Long> findAllMatchedIds(AnimalEntity animal);

    List<MatchingEntity> findAllByOwner(AnimalEntity owner);
}
