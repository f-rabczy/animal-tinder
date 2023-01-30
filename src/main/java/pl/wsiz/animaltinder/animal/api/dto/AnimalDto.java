package pl.wsiz.animaltinder.animal.api.dto;

import pl.wsiz.animaltinder.animal.domain.enums.AnimalCategory;


public record AnimalDto(Long id, String name, String description, int age, AnimalCategory category) {

}
