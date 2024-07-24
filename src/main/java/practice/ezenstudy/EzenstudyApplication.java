package practice.ezenstudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class EzenstudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EzenstudyApplication.class, args);
	}

}
