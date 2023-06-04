package ToDoApp.ToDoApplication.controllers;

import ToDoApp.ToDoApplication.models.ToDoItem;
import ToDoApp.ToDoApplication.models.User;
import ToDoApp.ToDoApplication.services.SecurityService;
import ToDoApp.ToDoApplication.services.UserService;
import ToDoApp.ToDoApplication.validator.UserValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model) {
        System.out.println("Get mapping registration");
        model.addAttribute("user", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        System.out.println("Post mapping registration");
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            return "redirect:/error";
        }

        userService.save(user);

        try {
            securityService.autoLogin(user.getUsername(), user.getConfirmPassword());
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(String error, String logout) {
        System.out.println("Get mapping login");
        if (logout != null) {
            System.out.println("BY LOG OUT");
        }
        if (error != null) {
            System.out.println("BY LOGIN ERROR");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        System.out.println("Get mapping logout");
        return "logout";
    }
}
