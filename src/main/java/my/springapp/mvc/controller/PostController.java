package my.springapp.mvc.controller;

import my.springapp.mvc.model.Post;
import my.springapp.mvc.service.PostService;
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
    private PostService postService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("entities", postService.listPosts());
        return "post/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("post", new Post());
        return "post/form";
    }

    @RequestMapping(value= "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("post") Post post, BindingResult result, Model model){
        if (!result.hasErrors()) {
            postService.addPost(post);
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        return "post/form";
    }

    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("Post", postService.getPostById(id));
        return "post/list";
    }

    @RequestMapping(value= "/update", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("post") Post post, BindingResult result, Model model){
        if (!result.hasErrors()) {
            postService.addPost(post);
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        return "post/form";
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") int id){
        postService.removePost(id);
        return "redirect:/posts";
    }

}
