package test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TestController {

    @RequestMapping("/test2")
    public String index() {
        System.err.println("This is protected controller access");
        return "Greetings from /test2";
    }

    @RequestMapping("/home")
    public String index2() {
        System.err.println("This is unprotected controller access");
        return "Greetings from /home";
    }


    @RequestMapping("/login")
    public String login() {
        System.err.println("Logging in");
        return "Greetings from /login";
    }


}