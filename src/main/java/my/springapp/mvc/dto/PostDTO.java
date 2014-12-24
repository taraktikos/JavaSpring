package my.springapp.mvc.dto;

import my.springapp.mvc.entity.Tag;
import my.springapp.mvc.entity.User;

import java.util.HashSet;
import java.util.Set;

public class PostDTO {

    private Long id;

    private String title;

    private String text;

    private User user;

    protected Set<Tag> tags = new HashSet<Tag>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
