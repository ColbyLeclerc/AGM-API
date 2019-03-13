package io.colby;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@Component(value = "io.colby")
@EnableJpaRepositories(basePackages="io.colby.model.repository")
public class Application {

    private static final boolean DEV_MODE = true;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    public static boolean isDevMode(){
        return DEV_MODE;
    }

}