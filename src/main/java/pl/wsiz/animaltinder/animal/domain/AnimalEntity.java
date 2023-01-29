package pl.wsiz.animaltinder.animal.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wsiz.animaltinder.user.domain.UserEntity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

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

    @Column(name = "CITY")
    @NotNull
    private String city;

    @Column(name = "COUNTY")
    @NotNull
    private String county;

    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "user_id")
    private UserEntity user;

    
    @OneToMany(mappedBy = "owner",cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    private Set<InteractionRecordEntity> animalsInteractionHistory = new HashSet<>();

}
