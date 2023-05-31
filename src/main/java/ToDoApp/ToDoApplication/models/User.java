package ToDoApp.ToDoApplication.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;

    private String password;

    @OneToMany
    @JoinTable(name = "user_todos", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "todo"))
    public Set<ToDoItem> toDoItemSet;

    @Transient
    private String confirmPassword;

    @Override
    public String toString() {
        return String.format("User{id=%d, username='%s', password='%s'}",
                id, username, password);
    }

}
