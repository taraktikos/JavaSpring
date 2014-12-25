package my.springapp.mvc.service;

import my.springapp.mvc.entity.User;
import my.springapp.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PersistenceContext
    private EntityManager em;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    public void save(User user) {
        if (user.getId() == null) {
            userRepository.save(user);
        } else {
            User savedUser = userRepository.findOne(user.getId());
            savedUser.setName(user.getName());
            savedUser.setUsername(user.getUsername());
            savedUser.setPassword(user.getPassword());
            em.merge(savedUser);
        }
    }

    public void delete(Long id) {
        userRepository.delete(id);
    }
}
