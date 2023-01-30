package pl.wsiz.animaltinder.user.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NotificationDto {

    private Long id;
    private String content;
    private String time;
}
