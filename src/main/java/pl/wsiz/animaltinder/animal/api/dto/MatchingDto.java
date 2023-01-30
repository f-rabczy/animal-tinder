package pl.wsiz.animaltinder.animal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.wsiz.animaltinder.animal.domain.enums.AnimalCategory;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class MatchingDto {

    private Long id;
    private String name;
    private AnimalCategory category;
    private LocalDate time;
    private Long chatId;

}
