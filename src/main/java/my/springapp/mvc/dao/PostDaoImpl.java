package my.springapp.mvc.dao;

import my.springapp.mvc.model.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDaoImpl implements PostDao {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Override
    public void addPost(Post p) {
        sessionFactory
                .getCurrentSession()
                .persist(p);
    }

    @Override
    public void updatePost(Post p) {
        sessionFactory
                .getCurrentSession()
                .update(p);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Post> listPosts() {
        return sessionFactory
                .getCurrentSession()
                .createQuery("from Post")
                .list();
    }

    @Override
    public Post getPostById(int id) {
        return (Post) sessionFactory
                .getCurrentSession()
                .load(Post.class, new Integer(id));
    }

    @Override
    public void removePost(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Post p = (Post) session.load(Post.class, new Integer(id));
        if(null != p){
            session.delete(p);
        }
    }

}
