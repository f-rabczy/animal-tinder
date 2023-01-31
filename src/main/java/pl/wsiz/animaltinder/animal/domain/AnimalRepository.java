package pl.wsiz.animaltinder.animal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.wsiz.animaltinder.animal.api.dto.AnimalDto;
import pl.wsiz.animaltinder.animal.domain.enums.AnimalCategory;
import pl.wsiz.animaltinder.user.domain.UserEntity;

import java.util.List;

public interface AnimalRepository extends JpaRepository<AnimalEntity, Long> {

    List<AnimalEntity> findAllByCityAndCountyAndCategoryAndUserNot(String city, String county, AnimalCategory category, UserEntity user);

    @Query(value = "select a from AnimalEntity  a where a.user.id = :userId")
    List<AnimalEntity> findAllByUserId(Long userId);
}
