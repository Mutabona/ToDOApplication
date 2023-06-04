package ToDoApp.ToDoApplication.services;

import ToDoApp.ToDoApplication.models.ToDoItem;
import ToDoApp.ToDoApplication.repositories.ToDoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class ToDoItemService {

    @Autowired
    private ToDoItemRepository toDoItemRepository;

    public Iterable<ToDoItem> getAll() {
        return toDoItemRepository.findAll();
    }

    public Iterable<ToDoItem> getAllByOwnerId(Long ownerId) {
        return toDoItemRepository.findByOwnerId(ownerId);
    }

    public Optional<ToDoItem> getById(Long id) {
        return toDoItemRepository.findById(id);
    }

    public ToDoItem save(ToDoItem toDoItem) {
        if (toDoItem.getId() == null) {
            toDoItem.setCreatedAt(Instant.now());
        }

        toDoItem.setUpdatedAt(Instant.now());
        System.out.println("To do item saved");
        return toDoItemRepository.save(toDoItem);
    }

    public void delete(ToDoItem toDoItem) {
        toDoItemRepository.delete(toDoItem);
        System.out.println("To do item deleted");
    }
}
