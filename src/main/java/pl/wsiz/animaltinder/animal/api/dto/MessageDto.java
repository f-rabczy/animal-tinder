package pl.wsiz.animaltinder.animal.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class MessageDto {

    private Long id;
    private Long senderId;
    private String content;
    private LocalDateTime time;
}
