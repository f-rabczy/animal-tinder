package pl.wsiz.animaltinder.animal.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wsiz.animaltinder.user.domain.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ANIMALS")
@Data
@NoArgsConstructor
public class AnimalEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    @NotNull
    private String name;

    @Column(name = "DESCRIPTION")
    @NotNull
    private String description;

    @Column(name = "AGE")
    @NotNull
    private int age;

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    private AnimalCategory category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
