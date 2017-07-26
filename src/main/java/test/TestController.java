package test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TestController {

    @RequestMapping("/home")
    public String home() {
        System.err.println("This is unprotected controller access");
        return "Greetings from /home";
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping("/test")
    public String index() {
        System.err.println("This is protected controller access USER");
        return "Greetings from /test2";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping("/admin")
    public String admin() {
        System.err.println("This is protected controller access ADMIN");
        return "Greetings from /home";
    }


    @RequestMapping("/login")
    public String login() {
        System.err.println("Logging in");
        return "Greetings from /login";
    }


}