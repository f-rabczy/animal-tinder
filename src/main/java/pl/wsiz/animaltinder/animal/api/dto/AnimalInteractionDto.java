package pl.wsiz.animaltinder.animal.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AnimalInteractionDto {

    private Long receiverAnimalId;
}
