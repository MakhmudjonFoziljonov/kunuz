package com.kunuz.util;

import com.kunuz.exps.AppForbiddenException;
import com.kunuz.enums.ProfileRole;

public class SpringSecurityUtil {

    public static void checkRoleExists(String profileRole, ProfileRole... requiredRoles) {
        for (ProfileRole requiredRole : requiredRoles) {
            if (requiredRole.name().equals(profileRole)) {
                return;
            }
        }
        throw new AppForbiddenException("Forbidden");
    }
}
