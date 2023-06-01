package ToDoApp.ToDoApplication.models;

import ToDoApp.ToDoApplication.services.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "todo_items")
public class ToDoItem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String description;

    private Boolean isComplete;

    private Instant createdAt;

    private Instant updatedAt;

    @Column(name = "ownerId")
    private Long ownerId;


    @Override
    public String toString() {
        return String.format("ToDoItem{id=%d, description='%s', isComplete='%s', createdAt='%s', updatedAt='%s', ownerId='%d'}",
                id, description, isComplete, createdAt, updatedAt, ownerId);
    }
}
