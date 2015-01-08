package my.springapp.mvc.security;

import my.springapp.mvc.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Map;

public class AppPermissionEvaluator implements PermissionEvaluator {

    @Resource(name="permissions")
    private Map permissions;

    @Autowired
    private PostPermissionEvaluator postPermissionEvaluator;

    @Autowired
    private UserPermissionEvaluator userPermissionEvaluator;

    private String getRole(Authentication authentication) {
        return authentication.getAuthorities().iterator().next().getAuthority();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String role = getRole(authentication);
        return hasPermission(role, permission, targetDomainObject, authentication);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

    @Transactional
    public boolean hasPermission(String role, Object permission, Object domain, Authentication authentication) {
        if (permissions.containsKey(role)) {
            Permission userPermission = (Permission) permissions.get(role);
            if (userPermission.getObjects().containsKey(domain.getClass().getName())) {
                for (String action: userPermission.getObjects().get(domain.getClass().getName())) {
                    if (action.equals(permission + "_ALL")) {
                        return true;
                    } else if (action.equals(permission + "_OWN")) {
                        Long userId = ((UserSecurity)authentication.getPrincipal()).getId();
                        if (domain instanceof Post) {
                            Post post = (Post) domain;
                            if (post.getOwnerId().equals(userId)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
