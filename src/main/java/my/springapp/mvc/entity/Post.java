package my.springapp.mvc.entity;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="posts")
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @Size(min=4, max=255)
    private String title;

    @NotEmpty
    private String text;

    @NotNull
    @ManyToOne
    @JoinColumn(name ="user_id", nullable=false)
    private User user;

    @DateTimeFormat(pattern="MM/dd/yyyy")
    @Column(name="created_at")
    private Date createdAt;

    public Post() {
        createdAt = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }
}
