package x.y;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class GradleprogApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradleprogApplication.class, args);
    }
    // Add a simple GET endpoint
    @GetMapping("/")
    public String home() {
        return "Hello, World!";
    }
}
// This is a simple Spring Boot application with a single GET endpoint that returns "Hello, World!". The application is packaged as a JAR file and can be run using the command `java -jar GradleprogApplication.jar`.

