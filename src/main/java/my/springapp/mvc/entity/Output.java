package my.springapp.mvc.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="output")
public class Output {

    @Id
    private String word;

    private int count;

    public int getCount() {
        return count;
    }

    public String getWord() {
        return word;
    }
}
