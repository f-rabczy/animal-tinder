package pl.wsiz.animaltinder.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

interface UserRepository extends JpaRepository<UserEntity,Long> {

    @Query(value = "select u from UserEntity u join fetch u.roleEntities where u.username =:username")
    Optional<UserEntity> findUserWithRolesByUsername(String username);

    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);
}
