package io.nuggets.chicken_nugget;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ChickenNuggetApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChickenNuggetApplication.class, args);
	}

}
