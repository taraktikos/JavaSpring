package my.springapp.mvc.controller;

import my.springapp.mvc.model.Post;
import my.springapp.mvc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public String listPosts(Model model) {
        model.addAttribute("entities", this.postService.listPosts());
        return "post/list";
    }

    //For add and update Post both
    @RequestMapping(value= "/post/add", method = RequestMethod.POST)
    public String addPost(@ModelAttribute("Post") Post p){

        if(p.getId() == 0){
            //new Post, add it
            this.postService.addPost(p);
        }else{
            //existing Post, call update
            this.postService.updatePost(p);
        }

        return "redirect:/posts";

    }

    @RequestMapping("/remove/{id}")
    public String removePost(@PathVariable("id") int id){

        this.postService.removePost(id);
        return "redirect:/posts";
    }

    @RequestMapping("/edit/{id}")
    public String editPost(@PathVariable("id") int id, Model model){
        model.addAttribute("Post", this.postService.getPostById(id));
        model.addAttribute("listPosts", this.postService.listPosts());
        return "post/list";
    }

}
