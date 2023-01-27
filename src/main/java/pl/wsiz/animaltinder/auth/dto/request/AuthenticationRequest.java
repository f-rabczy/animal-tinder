package pl.wsiz.animaltinder.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema
public class AuthenticationRequest {

    @Schema(description = "username", type = "string", example = "Tester1")
    private String username;
    @Schema(description = "password", type = "string", example = "Tester1")
    private String password;

}
