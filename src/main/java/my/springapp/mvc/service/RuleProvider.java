package my.springapp.mvc.service;

import my.springapp.mvc.entity.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Created by Taras S. on 23.03.15.
 */
@Service
public class RuleProvider {
    List<Rule> rules = new ArrayList<>();

    Map<Predicate<Post>, Rule> ruleMap = new HashMap<>();

    public RuleProvider() {
        rules.add(post ->  {
                if (post.getUser().getRole().equals("ROLE_ADMIN")) {
                    post.setText(post.getText() + " {Added by admin}");
                }
            });
        rules.add(post ->  {
                if (post.getUser().getRole().equals("ROLE_MANAGER")) {
                    post.setText(post.getText() + " {Added by manager}");
                }
            });
        rules.add(post -> {
            if (post.getTags().size() > 2) {
                post.setText(post.getText() + " {More 2 tags}");
            }
        });

        ruleMap.put(p -> p.getTags().size() < 2, post -> post.setText(post.getText() + " {Less 2 tags}"));
    }

    public void run(Post post) {
        rules.forEach(rule -> rule.run(post));

        ruleMap.forEach((k,v) -> {
            if (k.test(post)) {
                v.run(post);
            }
        });
    }
}
