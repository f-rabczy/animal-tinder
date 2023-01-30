package pl.wsiz.animaltinder.user.domain;


import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wsiz.animaltinder.animal.domain.AnimalEntity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "FIRST_NAME")
    @Size(min = 2)
    @NotNull
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotNull
    @Size(min = 2)
    private String lastName;

    @Column(name = "USERNAME")
    @NotNull
    @Size(min = 3)
    private String username;

    @Column(name = "PASSWORD")
    @NotNull
    private String password;

    @Column(name = "EMAIL")
    @NotNull
    @Email
    private String email;

    @Column(name = "IS_BANNED")
    @NotNull
    private boolean banned = false;

    @Column(name = "IS_SUSPENDED")
    @NotNull
    private boolean suspended = false;

    @Column(name = "BANNED_TIME")
    private LocalDate bannedTime;

    @Column(name = "SUSPENDED_TIME")
    private LocalDate suspendedTime;

    @Column(name = "SUSPENDED_UNTIL")
    private LocalDate suspendedUntil;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<AnimalEntity> animals = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRoleEntity> roleEntities = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = {CascadeType.ALL})
    private List<NotificationEntity> notifications = new ArrayList<>();

    public void addRole(UserRoleEntity roleEntity) {
        this.roleEntities.add(roleEntity);
    }

}
