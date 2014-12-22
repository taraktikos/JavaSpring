package my.springapp.mvc.formatter;

import my.springapp.mvc.entity.User;
import my.springapp.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class UserFormatter implements Formatter<User> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User parse(String s, Locale locale) throws ParseException {
        return userRepository.findOne(Long.parseLong(s));
    }

    @Override
    public String print(User user, Locale locale) {
        return user.getId().toString();
    }
}
