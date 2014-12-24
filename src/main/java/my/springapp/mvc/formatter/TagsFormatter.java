package my.springapp.mvc.formatter;

import my.springapp.mvc.entity.Tag;
import my.springapp.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
public class TagsFormatter implements Formatter<Set<Tag>> {

    @Override
    public Set<Tag> parse(String s, Locale locale) throws ParseException {
        Set<Tag> tags = new HashSet<Tag>();
        for(String tag: s.split(",")) {
            tag = tag.trim();
            if (tag.length() > 0) {
                tags.add(new Tag(tag.trim()));
            }
        }
        return tags;
    }

    @Override
    public String print(Set<Tag> tags, Locale locale) {
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
