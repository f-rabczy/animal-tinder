package pl.wsiz.animaltinder.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.wsiz.animaltinder.auth.domain.CustomUserDetails;
import pl.wsiz.animaltinder.auth.exception.BusinessException;
import pl.wsiz.animaltinder.auth.exception.ErrorMessage;
import pl.wsiz.animaltinder.user.domain.UserService;


@RequiredArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails customUserDetails = userService.getOptionalOfUserByUsername(username)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User with given username not found"));
        validateUserSuspensionAndBan(customUserDetails);
        return customUserDetails;
    }

    private void validateUserSuspensionAndBan(CustomUserDetails customUserDetails){
        if(customUserDetails.isSuspended()){
            throw new BusinessException(ErrorMessage.USER_ACCOUNT_SUSPENDED,"Suspended until " + customUserDetails.getSuspendedUntil());
        }
        if(customUserDetails.isBanned()){
            throw new BusinessException(ErrorMessage.USER_ACCOUNT_BANNED);
        }
    }

}
