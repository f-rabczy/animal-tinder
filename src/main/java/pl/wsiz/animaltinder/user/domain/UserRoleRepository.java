package pl.wsiz.animaltinder.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    @Query(value = "select u from UserRoleEntity u where u.name =:role")
    UserRoleEntity findByName(Role role);

}
