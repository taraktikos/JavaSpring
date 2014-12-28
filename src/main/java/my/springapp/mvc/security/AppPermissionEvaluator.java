package my.springapp.mvc.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

public class AppPermissionEvaluator implements PermissionEvaluator {

    @Resource(name="permissions")
    private Map permissions;

    @Autowired
    private RoleHierarchy roleHierarchy;

    private String getRole(Authentication authentication) {
        String highestRole = null;

        try {
            Collection<? extends GrantedAuthority> authorities = roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities());
            for (GrantedAuthority auth: authorities) {
                highestRole = auth.getAuthority();
                break;
            }
            System.out.println("Highest role hiearchy: " + roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities()));

        } catch (Exception e) {
            System.out.println("No authorities assigned");
        }

        return highestRole;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String role = getRole(authentication);
        return hasPermission(role, permission, targetDomainObject);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

    public boolean hasPermission(String role, Object permission, Object domain) {
        if (permissions.containsKey(role)) {
            Permission userPermission = (Permission) permissions.get(role);
            if (userPermission.getObjects().containsKey(domain.getClass().getName())) {
                for (String action: userPermission.getObjects().get(domain.getClass().getName())) {
                    if (action.equals(permission)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
