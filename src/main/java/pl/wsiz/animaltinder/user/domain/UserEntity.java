package pl.wsiz.animaltinder.user.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<UserRoleEntity> roleEntities = new HashSet<>();

    public void addRole(UserRoleEntity roleEntity) {
        this.roleEntities.add(roleEntity);
    }

}
