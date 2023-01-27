package pl.wsiz.animaltinder.auth.domain;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.wsiz.animaltinder.user.domain.UserEntity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CustomUserDetails implements UserDetails {

    private final String userName;
    private final String password;
    private final boolean active;
    private final List<GrantedAuthority> authorities;
    private final Long id;
    private final boolean suspended;
    private final boolean banned;
    private final LocalDate suspendedUntil;

    public CustomUserDetails(UserEntity user) {
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.active = true;
        this.authorities = user.getRoleEntities().stream()
                .map(roleEntity -> new SimpleGrantedAuthority("ROLE_" + roleEntity.getName().name()))
                .collect(Collectors.toList());
        this.id = user.getId();
        this.suspended = user.isSuspended();
        this.banned = user.isBanned();
        this.suspendedUntil = suspended ? user.getSuspendedUntil() : null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

}
