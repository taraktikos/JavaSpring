package my.springapp.mvc.dao;

import my.springapp.mvc.model.Post;

import java.util.List;

public interface PostDao {

    public void addPost(Post p);
    public void updatePost(Post p);
    public List<Post> listPosts();
    public Post getPostById(int id);
    public void removePost(int id);

}