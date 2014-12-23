package my.springapp.mvc.controller;

import my.springapp.mvc.entity.Post;
import my.springapp.mvc.entity.Tag;
import my.springapp.mvc.entity.User;
import my.springapp.mvc.repository.PostRepository;
import my.springapp.mvc.repository.TagRepository;
import my.springapp.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @PersistenceContext
    private EntityManager em;

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
    @Transactional
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String add(@Valid @ModelAttribute("post") Post post, BindingResult result, Model model){
        Set<Tag> savedTags = new HashSet<Tag>();
        for (Tag tag: post.getTags()) {
            Tag savedTag = tagRepository.findByName(tag.getName());
            if (savedTag != null) {
                savedTags.add(savedTag);
            }
        }
        post.getTags().removeAll(savedTags);
        post.getTags().addAll(savedTags);
        if (!result.hasErrors()) {
            em.merge(post);
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        model.addAttribute("users", userRepository.findAll());
        return "post/form";
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") Long id, Model model){
        model.addAttribute("post", postRepository.findOne(id));
        model.addAttribute("users", userRepository.findAll());
        return "post/form";
    }

    @Transactional
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute("post") Post post, BindingResult result, @PathVariable("id") Long id, Model model){
        if (!result.hasErrors()) {
            Post savedPost = postRepository.findOne(id);
            if (savedPost == null) {
                postRepository.save(post);
            } else {
                savedPost.setTitle(post.getTitle());
                savedPost.setText(post.getText());
                savedPost.setUser(post.getUser());
                savedPost.getTags().clear();
                for (Tag tag: post.getTags()) {
                    Tag savedTag = tagRepository.findByName(tag.getName());
                    if (savedTag != null) {
                        savedPost.getTags().add(savedTag);
                    } else {
                        savedPost.getTags().add(tag);
                    }
                }
                em.merge(savedPost);
            }
            return "redirect:/posts";
        }
        model.addAttribute("post", post);
        model.addAttribute("users", userRepository.findAll());
        return "post/form";
    }

    @RequestMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id){
        postRepository.delete(id);
        return "redirect:/posts";
    }

}
