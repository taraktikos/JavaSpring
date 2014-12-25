package my.springapp.mvc.formatter;

import my.springapp.mvc.entity.Tag;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Set;

@Component
public class TagsFormatter {

    public Set<Tag> parse(String s) {
        Set<Tag> tags = new HashSet<Tag>();
        for(String tag: s.split(",")) {
            tag = tag.trim();
            if (tag.length() > 0) {
                tags.add(new Tag(tag.trim()));
            }
        }
        return tags;
    }

    public String print(Set<Tag> tags) {
        StringBuilder builder = new StringBuilder();
        int counter = 1;
        for (Tag tag: tags) {
            builder.append(tag.getName());
            if (counter < tags.size()) {
                builder.append(", ");
            }
            counter ++;
        }
        return builder.toString();
    }

}
