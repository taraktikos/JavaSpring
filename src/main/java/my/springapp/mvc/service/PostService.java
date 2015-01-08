package my.springapp.mvc.service;

import my.springapp.mvc.entity.Post;
import my.springapp.mvc.entity.Tag;
import my.springapp.mvc.repository.PostRepository;
import my.springapp.mvc.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostFilter("hasPermission(filterObject, 'READ')")
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @PostAuthorize("hasPermission(returnObject, 'READ')")
    public Post findOne(Long id) {
        return postRepository.findOne(id);
    }

    @PreAuthorize("hasPermission(#post, 'WRITE')")
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

    @PreAuthorize("hasPermission(#post, 'DELETE')")
    public void delete(Post post) {
        postRepository.delete(post.getId());
    }

}
