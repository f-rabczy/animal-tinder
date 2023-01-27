package pl.wsiz.animaltinder.user.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
@Data
public class Address {

    @Column(name = "CITY")
    @NotNull
    private String city;

    @Column(name = "COUNTY")
    @NotNull
    private String county;

    @Column(name = "VOIVODESHIP")
    @NotNull
    private String voivodeship;
}
