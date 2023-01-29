package pl.wsiz.animaltinder.user.api.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserCreateDto {

    @NotEmpty(message = "Username must not be empty")
    private String username;
    @NotEmpty(message = "Password must not be empty")
    private String password;
    @NotEmpty(message = "Email must not be empty")
    private String email;
    @NotEmpty(message = "First name must not be empty")
    private String firstName;
    @NotEmpty(message = "Last name must not be empty")
    private String lastName;
    @NotEmpty(message = "Last name must not be empty")
    private String city;
    @NotEmpty(message = "Last name must not be empty")
    private String county;
    @NotEmpty(message = "Last name must not be empty")
    private String voivodeship;
}
