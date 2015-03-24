package my.springapp.mvc.service;

import my.springapp.mvc.entity.Post;

/**
 * Created by Taras S. on 23.03.15.
 */
public interface Rule {
    void run(Post post);
}
