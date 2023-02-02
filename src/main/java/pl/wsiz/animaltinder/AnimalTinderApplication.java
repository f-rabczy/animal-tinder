package pl.wsiz.animaltinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PreDestroy;
import java.nio.file.Paths;

@SpringBootApplication
public class AnimalTinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnimalTinderApplication.class, args);
    }
}
