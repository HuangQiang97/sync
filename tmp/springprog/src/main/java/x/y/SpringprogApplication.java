package x.y;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@RestController
public class SpringprogApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringprogApplication.class, args);
    }
    @GetMapping("/")
    public  String helli(){
        return "hello";
    }

}
