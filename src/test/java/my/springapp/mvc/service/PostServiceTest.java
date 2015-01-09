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

    @Test
    public void findAllTest() throws Exception {
        loginUser();
        assertEquals(1, postService.findAll().size());

        loginManager();
        assertEquals(3, postService.findAll().size());

        loginAdmin();
        assertEquals(3, postService.findAll().size());
    }

    @Test
    public void findOneTest() throws Exception {
        loginUser();
        assertEquals(posts.get(2).getId(), postService.findOne(posts.get(2).getId()).getId());
    }

    @Test(expected = AccessDeniedException.class)
    public void findOneAccessDeniedTest() throws Exception {
        loginUser();
        postService.findOne(posts.get(0).getId());
    }

    @Test
    @Transactional
    public void saveTest() throws Exception {
        loginAdmin();
        Post post = postService.findOne(posts.get(0).getId());
        User user = userRepository.findOne(2L);
        post.setUser(user);
        post.setTitle("New title");
        postService.save(post);
    }

    @Test(expected = AccessDeniedException.class)
    @Transactional
    public void saveAccessDeniedManagerTest() throws Exception {
        loginManager();
        Post post = postService.findOne(posts.get(0).getId());
        User user = userRepository.findOne(2L);
        post.setUser(user);
        postService.save(post);
    }

    @Test(expected = AccessDeniedException.class)
    public void saveAccessDeniedUserTest() throws Exception {
        loginUser();
        postService.save(posts.get(2));
    }

    @Test
    @Transactional
    public void deleteTest() throws Exception {
        loginAdmin();
        Post post = postService.findOne(posts.get(0).getId());
        postService.delete(post);

        loginManager();
        post = postService.findOne(posts.get(1).getId());
        postService.delete(post);
    }

    @Test(expected = AccessDeniedException.class)
    @Transactional
    public void deleteAccessDeniedUserTest() throws Exception {
        loginUser();
        Post post = postService.findOne(posts.get(0).getId());
        postService.delete(post);
    }

    @Test(expected = AccessDeniedException.class)
    @Transactional
    public void deleteAccessDeniedManagerTest() throws Exception {
        loginManager();
        Post post = postService.findOne(posts.get(0).getId());
        postService.delete(post);
    }

}
