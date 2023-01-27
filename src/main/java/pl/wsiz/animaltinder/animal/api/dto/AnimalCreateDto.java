package pl.wsiz.animaltinder.animal.api.dto;

import pl.wsiz.animaltinder.animal.domain.AnimalCategory;

import java.time.LocalDate;

public class AnimalCreateDto {

    private String name;
    private String description;
    private LocalDate dateOfBirth;
    private AnimalCategory animalCategory;
}
