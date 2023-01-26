package pl.wsiz.animaltinder.auth.util;

import org.springframework.security.core.context.SecurityContextHolder;
import pl.wsiz.animaltinder.auth.domain.CustomUserDetails;

public class UserDetailsUtil {

    public static Long getCurrentUserId() {
        CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return customUserDetails.getId();
    }

}
