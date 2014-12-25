package my.springapp.mvc.service;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import my.springapp.mvc.dto.PostDTO;
import my.springapp.mvc.dto.PostListDTO;
import my.springapp.mvc.dto.UserDTO;
import my.springapp.mvc.dto.UserListDTO;
import my.springapp.mvc.entity.Post;
import my.springapp.mvc.entity.User;
import my.springapp.mvc.formatter.TagsFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappingService {

    @Autowired
    private UserService userService;

    @Autowired
    private TagsFormatter tagsFormatter;

    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    MapperFacade mapper = mapperFactory.getMapperFacade();

    public User userDTOToUser(UserDTO userDTO) {
        return mapper.map(userDTO, User.class);
    }

    public User userDTOToUser(UserDTO userDTO, User user) {
        mapper.map(userDTO, user);
        return user;
    }

    public UserDTO userToUserDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    public Post postDTOToPost(PostDTO postDTO) {
        return postDTOToPost(postDTO, new Post());
    }

    public Post postDTOToPost(PostDTO postDTO, Post post) {
        mapper.map(postDTO, post);
        post.setTags(tagsFormatter.parse(postDTO.getTags()));
        post.setUser(userService.findOne(postDTO.getUserId()));
        return post;
    }

    public PostDTO postToPostDTO(Post post) {
        mapperFactory.classMap(Post.class, PostDTO.class)
                .field("user.id", "userId")
                .exclude("tags")
                .byDefault()
                .register();
        PostDTO postDTO = mapper.map(post, PostDTO.class);
        postDTO.setTags(tagsFormatter.print(post.getTags()));
        return postDTO;
    }

    public List<UserListDTO> userListToUserListDTO(List<User> users) {
        return mapper.mapAsList(users, UserListDTO.class);
    }

    public List<PostListDTO> postListToPostListDTO(List<Post> posts) {
        mapperFactory.classMap(Post.class, PostListDTO.class)
                .field("user.name", "userName")
                .exclude("tags")
                .byDefault()
                .customize(new CustomMapper<Post, PostListDTO>() {
                    @Override
                    public void mapAtoB(Post post, PostListDTO postListDTO, MappingContext context) {
                        super.mapAtoB(post, postListDTO, context);
                        postListDTO.setTags(tagsFormatter.print(post.getTags()));
                    }
                })
                .register();
        return mapper.mapAsList(posts, PostListDTO.class);
    }

}
