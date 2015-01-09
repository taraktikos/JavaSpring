package my.springapp.mvc.security;

import my.springapp.mvc.entity.Post;
import my.springapp.mvc.entity.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppPermissionEvaluator implements PermissionEvaluator {

    @Resource(name="permissions")
    private Map permissions;

    private String getRole(Authentication authentication) {
        return authentication.getAuthorities().iterator().next().getAuthority();
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        String role = getRole(authentication);
        return hasPermission(role, (String)permission, targetDomainObject, authentication);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }

    @Transactional
    public boolean hasPermission(String role, String permission, Object domain, Authentication authentication) {
        if (permissions.containsKey(role)) {
            List<String> userPermission = (ArrayList<String>) permissions.get(role);
            if (userPermission.contains(permission + "_ALL")) {
                return true;
            } else if (userPermission.contains(permission + "_OWN")) {
                Long userId = ((UserSecurity)authentication.getPrincipal()).getId();
                if (permission.contains("POST_") && domain instanceof Post) {
                    Post post = (Post) domain;
                    if (post.getOwnerId().equals(userId)) {
                        return true;
                    }
                } else if (permission.contains("USER_") && domain instanceof User) {
                    User user = (User) domain;
                    if (user.getId().equals(userId)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
