package com.kunuz.util;

import com.kunuz.config.CustomUserDetails;
import com.kunuz.exps.AppForbiddenException;
import com.kunuz.enums.ProfileRole;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableWebSecurity
public class SpringSecurityUtil {

    public static void checkRoleExists(String profileRole, ProfileRole... requiredRoles) {
        for (ProfileRole requiredRole : requiredRoles) {
            if (requiredRole.name().equals(profileRole)) {
                return;
            }
        }
        throw new AppForbiddenException("Forbidden");
    }

    public static CustomUserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        System.out.println(user.getUsername());
        //Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) user.getAuthorities();
        // Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return user;
    }

    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
        return user.getId();
    }


}
