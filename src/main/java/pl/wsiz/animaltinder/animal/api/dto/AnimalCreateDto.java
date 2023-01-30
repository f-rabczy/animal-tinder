package pl.wsiz.animaltinder.animal.api.dto;

import lombok.Data;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
public class AnimalCreateDto {

    private String name;
    private String description;
    @PastOrPresent
    private LocalDate dateOfBirth;
    private String category;
    private String city;
    private String county;


    public AnimalCreateDto(String name, String description, LocalDate dateOfBirth, String category, String city, String county) {
        this.name = name;
        this.description = description;
        this.dateOfBirth = dateOfBirth;
        this.category = category.toUpperCase();
        this.city = city;
        this.county = county;
    }
}
