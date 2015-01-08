package my.springapp.mvc.service;


import my.springapp.mvc.AppTests;
import my.springapp.mvc.entity.Post;
import my.springapp.mvc.entity.User;
import my.springapp.mvc.repository.PostRepository;
import my.springapp.mvc.repository.UserRepository;
import my.springapp.mvc.security.UserSecurity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.transaction.Transactional;
import java.util.List;
import static org.junit.Assert.*;

public class PostServiceTest extends AppTests {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    List<Post> posts;

    @Before
    public void setup() {
        super.setup();
        posts = postRepository.findAll();
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    public void login(String role) {
        Authentication auth;
        if (role.equals("ROLE_ADMIN")) {
            auth = new UsernamePasswordAuthenticationToken("admin", "123456");
        } else if (role.equals("ROLE_MANAGER")) {
            auth = new UsernamePasswordAuthenticationToken("manager", "123456");
        } else {
            auth = new UsernamePasswordAuthenticationToken("user", "123456");
        }
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(auth);
    }

    @Test
    public void findAllTest() throws Exception {
//        assertEquals(0, postService.findAll().size());
        login("ROLE_USER");
        assertEquals(1, postService.findAll().size());

        login("ROLE_MANAGER");
        assertEquals(3, postService.findAll().size());

        login("ROLE_ADMIN");
        assertEquals(3, postService.findAll().size());
    }

    @Test
    public void findOneTest() throws Exception {
        login("ROLE_USER");
        assertEquals(posts.get(2).getId(), postService.findOne(posts.get(2).getId()).getId());
    }

    @Test(expected = AccessDeniedException.class)
    public void findOneAccessDeniedTest() throws Exception {
        login("ROLE_USER");
        postService.findOne(posts.get(0).getId());
    }

    @Test
    @Transactional
    public void saveTest() throws Exception {
        login("ROLE_ADMIN");
        Post post = postService.findOne(posts.get(0).getId());
        User user = userRepository.findOne(2L);
        post.setUser(user);
        post.setTitle("New title");
        postService.save(post);
    }

    @Test(expected = AccessDeniedException.class)
    @Transactional
    public void saveAccessDeniedManagerTest() throws Exception {
        login("ROLE_MANAGER");
        Post post = postService.findOne(posts.get(0).getId());
        User user = userRepository.findOne(2L);
        post.setUser(user);
        postService.save(post);
    }

    @Test(expected = AccessDeniedException.class)
    public void saveAccessDeniedUserTest() throws Exception {
        login("ROLE_USER");
        postService.save(posts.get(2));
    }
}
