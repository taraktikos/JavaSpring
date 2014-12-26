package my.springapp.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class AccessController {

    @RequestMapping("/login")
    public String login() {
        return "access/login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "access/login";
    }

    @RequestMapping("/denied")
    public String denied() {
        return "access/denied";
    }

}
