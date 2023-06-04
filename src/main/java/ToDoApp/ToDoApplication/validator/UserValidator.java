package ToDoApp.ToDoApplication.validator;


import ToDoApp.ToDoApplication.models.User;
import ToDoApp.ToDoApplication.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "Required");

        if (user.getUsername().length() < 3 || user.getUsername().length() > 32) {
            System.out.println("User " +user.getUsername() + " rejected");
            errors.rejectValue("username", "Size.userForm.username");
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            System.out.println("User " +user.getUsername() + " rejected");
            errors.rejectValue("username", "Duplicate.userForm.username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Required");

        if (user.getPassword().length() < 3 || user.getPassword().length() > 32) {
            System.out.println("User " +user.getUsername() + " rejected");
            errors.rejectValue("password", "Size.userForm.password");
        }

        if (!user.getConfirmPassword().equals(user.getPassword())) {
            System.out.println("User " +user.getUsername() + " rejected");
            errors.rejectValue("confirmPassword", "Different.userForm.password");
        }
    }
}