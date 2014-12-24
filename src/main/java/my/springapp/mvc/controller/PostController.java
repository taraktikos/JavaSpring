package my.springapp.mvc.controller;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import my.springapp.mvc.dto.PostDTO;
import my.springapp.mvc.entity.Post;
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

import javax.transaction.Transactional;
import javax.validation.Valid;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    MapperFacade mapper = mapperFactory.getMapperFacade();

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("entities", postService.findAll());
        return "post/list";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("post", new PostDTO());
        model.addAttribute("users", userService.findAll());
        return "post/form";
    }
    @Transactional
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("post") PostDTO postDTO, BindingResult result, Model model){
        Post post = mapper.map(postDTO, Post.class);
        if (!result.hasErrors()) {
            postService.save(post);
            return "redirect:/posts";
        }
        model.addAttribute("post", postDTO);
        model.addAttribute("users", userService.findAll());
        return "post/form";
    }

    @Transactional
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model){
        PostDTO postDTO = mapper.map(postService.findOne(id), PostDTO.class);
        model.addAttribute("post", postDTO);
        model.addAttribute("users", userService.findAll());
        return "post/form";
    }

    @Transactional
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("post") PostDTO postDTO, BindingResult result, @PathVariable("id") Long id, Model model){
        Post post = mapper.map(postDTO, Post.class);
        if (!result.hasErrors()) {
            post.setId(id);
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
