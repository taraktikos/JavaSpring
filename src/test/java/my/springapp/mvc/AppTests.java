package my.springapp.mvc;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml",
        "classpath:/parameters-test-context.xml",
        "file:src/main/webapp/WEB-INF/root-context.xml",
        "file:src/main/webapp/WEB-INF/spring-security.xml"
})
public class AppTests {
    private MockMvc mockMvc;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    protected WebApplicationContext wac;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(this.wac).build();
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

    public void loginUser() {
        login("ROLE_USER");
    }

    public void loginManager() {
        login("ROLE_MANAGER");
    }

    public void loginAdmin() {
        login("ROLE_ADMIN");
    }

}
