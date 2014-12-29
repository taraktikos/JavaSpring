package my.springapp.mvc.security;

import my.springapp.mvc.entity.Post;
import my.springapp.mvc.entity.User;
import my.springapp.mvc.service.UserService;
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

    @Autowired
    private UserService userService;

    private String getRole(Authentication authentication) {
        String highestRole = null;

        try {
            //Collection<? extends GrantedAuthority> authorities = roleHierarchy.getReachableGrantedAuthorities(authentication.getAuthorities());
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
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
        User user = userService.findByUsername(authentication.getName());
        return hasPermission(role, permission, targetDomainObject, user);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

    public boolean hasPermission(String role, Object permission, Object domain, User currentUser) {
        if (permissions.containsKey(role)) {
            Permission userPermission = (Permission) permissions.get(role);
            if (userPermission.getObjects().containsKey(domain.getClass().getName())) {
                for (String action: userPermission.getObjects().get(domain.getClass().getName())) {
                    if (action.equals(permission)) {
                        if (action.contains("_OWN")) {
                            if (domain instanceof Post) {
                                if (((Post) domain).getUser().getId() != currentUser.getId()) {
                                    return false;
                                }
                            }
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
