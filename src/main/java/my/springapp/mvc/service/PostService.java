package my.springapp.mvc.service;

import my.springapp.mvc.model.Post;

import java.util.List;

public interface PostService {

    public void addPost(Post p);
    public void updatePost(Post p);
    public List<Post> listPosts();
    public Post getPostById(int id);
    public void removePost(int id);

}
