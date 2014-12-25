package my.springapp.mvc.controller;

import my.springapp.mvc.dto.PostDTO;
import my.springapp.mvc.dto.PostListDTO;
import my.springapp.mvc.entity.Post;
import my.springapp.mvc.service.MappingService;
import my.springapp.mvc.service.PostService;
import my.springapp.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    MappingService mappingService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String list(Model model){
        List<PostListDTO> posts = mappingService.postListToPostListDTO(postService.findAll());
        model.addAttribute("posts", posts);
        return "post/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("post", new PostDTO());
        model.addAttribute("users", userService.findAll());
        return "post/form";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("post") PostDTO postDTO, BindingResult result, Model model){
        Post post = mappingService.postDTOToPost(postDTO);
        if (!result.hasErrors()) {
            postService.save(post);
            return "redirect:/posts";
        }
        model.addAttribute("post", postDTO);
        model.addAttribute("users", userService.findAll());
        return "post/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model){
        PostDTO postDTO = mappingService.postToPostDTO(postService.findOne(id));
        model.addAttribute("post", postDTO);
        model.addAttribute("users", userService.findAll());
        return "post/form";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("post") PostDTO postDTO, BindingResult result, @PathVariable("id") Long id, Model model){
        Post post = postService.findOne(id);
        mappingService.postDTOToPost(postDTO, post);
        if (!result.hasErrors()) {
            postService.save(post);
            return "redirect:/posts";
        }
        model.addAttribute("post", postDTO);
        model.addAttribute("users", userService.findAll());
        return "post/form";
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id){
        postService.delete(id);
        return "redirect:/posts";
    }

}
