package webapp.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.stereotype.Controller;

@Controller
@SpringBootApplication
public class WlpWebJavaSpringApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(WlpWebJavaSpringApplication.class, args);
    }
}
