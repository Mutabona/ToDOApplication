package ToDoApp.ToDoApplication.services;

import ToDoApp.ToDoApplication.models.User;
import ToDoApp.ToDoApplication.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Controller
public class SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

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

    @PostMapping("/login")
    public String login(@RequestBody User loginRequest, HttpServletRequest request, HttpServletResponse response)
    {
        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                loginRequest.getUsername(), loginRequest.getConfirmPassword());

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        try {
            securityContextRepository.saveContext(context, request, response);
        }
        catch (Exception e) {
            System.out.println(e);
            return "redirect:/error";
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        System.out.println("Get mapping registration");
        model.addAttribute("user", new User());

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("user") User user, BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Post mapping registration");
        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            return "redirect:/error";
        }

        userService.save(user);

        try {
            login(user, request, response);
        }
        catch (Exception e) {
            System.out.println(e);
        }

        return "redirect:/";
    }


    @GetMapping("/logout")
    public String logout() {
        System.out.println("Get mapping logout");
        return "logout";
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }
    private SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();
}
