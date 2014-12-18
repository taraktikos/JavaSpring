package my.springapp.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.springapp.mvc.model.Post;
import my.springapp.mvc.dao.PostDao;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Override
    @Transactional
    public void addPost(Post p) {
        postDao.addPost(p);
    }

    @Override
    @Transactional
    public void updatePost(Post p) {
        postDao.updatePost(p);
    }

    @Override
    @Transactional
    public List<Post> listPosts() {
        return postDao.listPosts();
    }

    @Override
    @Transactional
    public Post getPostById(int id) {
        return postDao.getPostById(id);
    }

    @Override
    @Transactional
    public void removePost(int id) {
        postDao.removePost(id);
    }


}
