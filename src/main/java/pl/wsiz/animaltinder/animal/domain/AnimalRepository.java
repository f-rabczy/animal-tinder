package pl.wsiz.animaltinder.animal.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsiz.animaltinder.animal.domain.enums.AnimalCategory;
import pl.wsiz.animaltinder.user.domain.UserEntity;

import java.util.List;

public interface AnimalRepository extends JpaRepository<AnimalEntity,Long> {

    List<AnimalEntity> findAllByCityAndCountyAndCategoryAndUserNot(String city, String county, AnimalCategory category, UserEntity user);
}
