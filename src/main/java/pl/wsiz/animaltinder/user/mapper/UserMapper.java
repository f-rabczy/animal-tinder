package pl.wsiz.animaltinder.user.mapper;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.wsiz.animaltinder.user.api.dto.UserCreateDto;
import pl.wsiz.animaltinder.user.api.dto.UserDto;
import pl.wsiz.animaltinder.user.domain.Role;
import pl.wsiz.animaltinder.user.domain.UserEntity;
import pl.wsiz.animaltinder.user.domain.UserRoleEntity;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class UserMapper {

    @Autowired
    private PasswordEncoder encoder;

    @Mapping(target = "password", source = "source", qualifiedByName = "encodePassword")
    public abstract UserEntity mapUserCreateRequestToUserEntity(UserCreateDto source);

    @Mapping(target = "name", source = "role")
    public abstract UserRoleEntity mapRoleEnumToUserRoleEntity(Role role);

    public abstract UserDto mapUserEntityToUserDTO(UserEntity source);

    @Named("encodePassword")
    protected String encodePassword(UserCreateDto source) {
        return encoder.encode(source.getPassword());
    }

}
