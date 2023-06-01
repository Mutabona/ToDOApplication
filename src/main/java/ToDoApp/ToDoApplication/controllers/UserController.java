package ToDoApp.ToDoApplication.controllers;

import ToDoApp.ToDoApplication.models.ToDoItem;
import ToDoApp.ToDoApplication.models.User;
import ToDoApp.ToDoApplication.services.SecurityService;
import ToDoApp.ToDoApplication.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/reg")
    public String registration(User user) {

        return "registration";
    }

    @PostMapping("/register")
    public String reg(@Valid User user, BindingResult result, Model model) {
        if (userService.findByUsername(user.getUsername()) != null) {
            return "redirect:/error";
        }
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());

        userService.save(newUser);
        securityService.autoLogin(user.getUsername(), user.getPassword());
        return String.format("redirect:/login");
    }
}
