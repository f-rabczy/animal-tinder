package pl.wsiz.animaltinder.animal.domain;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pl.wsiz.animaltinder.animal.api.dto.AnimalCreateDto;
import pl.wsiz.animaltinder.animal.api.dto.AnimalDto;
import pl.wsiz.animaltinder.user.domain.UserEntity;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @Mapping(source = "animalCreateDto", target = "age", qualifiedByName = "calculateAge")
    AnimalEntity mapToAnimalEntity(AnimalCreateDto animalCreateDto);

    AnimalDto mapToAnimalDto(AnimalEntity animal);

    @Named("calculateAge")
    default int calculateAge(AnimalCreateDto animalCreateDto){
        return Period.between(animalCreateDto.getDateOfBirth(), LocalDate.now()).getYears();
    }
}
