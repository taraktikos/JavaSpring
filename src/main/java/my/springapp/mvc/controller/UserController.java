package my.springapp.mvc.controller;

import my.springapp.mvc.dto.UserDTO;
import my.springapp.mvc.dto.UserListDTO;
import my.springapp.mvc.entity.User;
import my.springapp.mvc.service.MappingService;
import my.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
@Transactional
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MappingService mappingService;

    @Resource(name="permissions")
    private Map permissions;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String list(Model model){
        List<UserListDTO> users = mappingService.userListToUserListDTO(userService.findAll());
        model.addAttribute("users", users);
        return "user/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("user", new UserDTO());
        model.addAttribute("permissions", permissions);
        return "user/form";
    }

    @RequestMapping(value= "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model){
        User user = mappingService.userDTOToUser(userDTO);
        if (!result.hasErrors()) {
            userService.save(user);
            return "redirect:/users";
        }
        model.addAttribute("user", userDTO);
        model.addAttribute("permissions", permissions);
        return "user/form";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model){
        UserDTO userDTO = mappingService.userToUserDTO(userService.findOne(id));
        model.addAttribute("user", userDTO);
        model.addAttribute("permissions", permissions);
        return "user/form";
    }

    @RequestMapping(value= "/update/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, @PathVariable("id") Long id, Model model){
        User user = userService.findOne(id);
        mappingService.userDTOToUser(userDTO, user);
        if (!result.hasErrors()) {
            userService.save(user);
            return "redirect:/users";
        }
        model.addAttribute("user", userDTO);
        model.addAttribute("permissions", permissions);
        return "user/form";
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/users";
    }

}
