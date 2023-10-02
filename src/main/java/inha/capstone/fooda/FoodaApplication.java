package inha.capstone.fooda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FoodaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodaApplication.class, args);
    }

}
