package my.springapp.mvc.dao;

import my.springapp.mvc.model.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDaoImpl implements PostDao {

    private SessionFactory sessionFactory;
    
    public void setSessionFactory(SessionFactory sf){
        this.sessionFactory = sf;
    }

    @Override
    public void addPost(Post p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(p);
    }

    @Override
    public void updatePost(Post p) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(p);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Post> listPosts() {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from Post").list();
    }

    @Override
    public Post getPostById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        Post p = (Post) session.load(Post.class, new Integer(id));
        return p;
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
