package pl.wsiz.animaltinder.user.api.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserCreateDto {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String city;
    private String county;
    private String voivodeship;
}
