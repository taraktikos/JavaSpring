package my.springapp.mvc.service;

import my.springapp.mvc.entity.User;
import my.springapp.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

    @PostFilter("hasPermission(filterObject, 'USER_READ')")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAllBloggers() {
        return userRepository.findAll();
    }

    @PostAuthorize("hasPermission(returnObject, 'USER_READ')")
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @PreAuthorize("hasPermission(#user, 'USER_WRITE')")
    public void save(User user) {
        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else {
            User savedUser = userRepository.findOne(user.getId());
            savedUser.setName(user.getName());
            savedUser.setUsername(user.getUsername());
            if (user.getPassword() != null) {
                savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            em.merge(savedUser);
        }
    }
    
    @PreAuthorize("hasPermission(#user, 'USER_WRITE')")
    public void delete(User user) {
        userRepository.delete(user.getId());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
