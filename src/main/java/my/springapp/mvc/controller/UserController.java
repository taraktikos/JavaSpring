package my.springapp.mvc.controller;

import my.springapp.mvc.model.User;
import my.springapp.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("entities", userRepository.findAll());
        return "user/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("user", new User());
        return "user/form";
    }

    @RequestMapping(value= "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        if (!result.hasErrors()) {
            userRepository.save(user);
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "user/form";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userRepository.findOne(id));
        return "post/list";
    }

    @RequestMapping(value= "/update", method = RequestMethod.POST)
    public String updatePost(@Valid @ModelAttribute("user") User user, BindingResult result, Model model){
        if (!result.hasErrors()) {
            userRepository.save(user);
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "user/form";
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id){
        userRepository.delete(id);
        return "redirect:/users";
    }

}
