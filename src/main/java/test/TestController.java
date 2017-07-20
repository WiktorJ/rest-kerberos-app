package test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class TestController {

    @RequestMapping("/test2")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}