package my.springapp.mvc.service;

import my.springapp.mvc.entity.Post;
import my.springapp.mvc.entity.Tag;
import my.springapp.mvc.repository.PostRepository;
import my.springapp.mvc.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagRepository tagRepository;

    @PersistenceContext
    private EntityManager em;

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findOne(Long id) {
        return postRepository.findOne(id);
    }

    public void save(Post post) {
        Set<Tag> savedTags = new HashSet<Tag>();
        for (Tag tag: post.getTags()) {
            Tag savedTag = tagRepository.findByName(tag.getName());
            if (savedTag != null) {
                savedTags.add(savedTag);
            }
        }
        post.getTags().removeAll(savedTags);
        post.getTags().addAll(savedTags);
        em.merge(post);
    }

    public void delete(Long id) {
        postRepository.delete(id);
    }

}
