package my.springapp.mvc.service;


import my.springapp.mvc.AppTests;
import my.springapp.mvc.entity.User;
import my.springapp.mvc.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import javax.transaction.Transactional;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class UserServiceTest extends AppTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    List<User> users;

    @Before
    public void setup() {
        super.setup();
        users = userRepository.findAll();
    }
    
    @Test
    public void findAllTest() throws Exception {
        loginUser();
        assertEquals(0, userService.findAll().size());

        loginManager();
        assertEquals(1, userService.findAll().size());

        loginAdmin();
        assertEquals(3, userService.findAll().size());
    }

    @Test
    public void findOneTest() throws Exception {
        loginAdmin();
        assertEquals(users.get(1).getId(), userService.findOne(users.get(1).getId()).getId());

        loginManager();
        assertEquals(users.get(1).getId(), userService.findOne(users.get(1).getId()).getId());
    }

    @Test(expected = AccessDeniedException.class)
    public void findOneAccessDeniedTest() throws Exception {
        loginUser();
        assertEquals(users.get(2).getId(), userService.findOne(users.get(2).getId()).getId());
    }

    @Test(expected = AccessDeniedException.class)
    public void findOneManagerAccessDeniedTest() throws Exception {
        loginManager();
        assertEquals(users.get(2).getId(), userService.findOne(users.get(2).getId()).getId());
    }

    @Test
    @Transactional
    public void saveTest() throws Exception {
        loginAdmin();
        User user = userService.findOne(users.get(0).getId());
        user.setName("new name");
        userService.save(user);
    }

    @Test(expected = AccessDeniedException.class)
    @Transactional
    public void saveAccessDeniedManagerTest() throws Exception {
        loginManager();
        User user = userService.findOne(users.get(0).getId());
        user.setName("new name");
        userService.save(user);
    }

    @Test(expected = AccessDeniedException.class)
    public void saveAccessDeniedUserTest() throws Exception {
        loginUser();
        userService.save(users.get(0));
    }

    @Test
    @Transactional
    public void deleteTest() throws Exception {
        loginAdmin();
        userService.delete(users.get(0));
    }

    @Test(expected = AccessDeniedException.class)
    @Transactional
    public void deleteAccessDeniedUserTest() throws Exception {
        loginUser();
        userService.delete(users.get(0));
    }

    @Test(expected = AccessDeniedException.class)
    @Transactional
    public void deleteAccessDeniedManagerTest() throws Exception {
        loginManager();
        userService.delete(users.get(1));
    }
}
