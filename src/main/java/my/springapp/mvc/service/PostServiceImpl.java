package my.springapp.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.springapp.mvc.model.Post;
import my.springapp.mvc.dao.PostDao;

import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    public void addPost(Post p) {
        postDao.addPost(p);
    }

    @Override
    public void updatePost(Post p) {
        postDao.updatePost(p);
    }

    @Override
    public List<Post> listPosts() {
        return postDao.listPosts();
    }

    @Override
    public Post getPostById(int id) {
        return postDao.getPostById(id);
    }

    @Override
    public void removePost(int id) {
        postDao.removePost(id);
    }


}
