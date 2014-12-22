package my.springapp.mvc.controller;

import my.springapp.mvc.entity.Post;
import my.springapp.mvc.entity.User;
import my.springapp.mvc.repository.PostRepository;
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
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("entities", postRepository.findAll());
        return "post/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("post", new Post());
        model.addAttribute("users", userRepository.findAll());
        return "post/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("post") Post post, BindingResult result, Model model){
        if (!result.hasErrors()) {
            postRepository.save(post);
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        model.addAttribute("users", userRepository.findAll());
        return "post/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model){
        model.addAttribute("Post", postRepository.findOne(id));
        return "post/list";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("post") Post post, BindingResult result, @PathVariable("id") Long id, Model model){
        if (!result.hasErrors()) {
            postRepository.save(post);
            return "redirect:/posts";
        }
        return edit(id, model);
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id){
        postRepository.delete(id);
        return "redirect:/posts";
    }

}
