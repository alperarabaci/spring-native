package com.training.demo;

import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

/**
 * Started SpringNativeApplication in 2.693 seconds (JVM running for 3.232)
 * 
 * mvn clean spring-boot:build-image
 *  [INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time:  09:23 min
 * 
 * docker run -p 8080:8080 docker.io/library/spring-native-demo-1:0.0.1-SNAPSHOT
 * Started SpringNativeApplication in 0.143 seconds (JVM running for 0.146)
 * Started SpringNativeApplication in 0.133 seconds (JVM running for 0.136)
 * 
 * 
 * not worked, jmx not build:
 * https://stackoverflow.com/questions/31257968/how-to-access-jmx-interface-in-docker-from-outside
 * @author alper
 *
 */
@SpringBootApplication
public class SpringNativeApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringNativeApplication.class, args);
	}
	
	@Bean
	ApplicationRunner initializer(CustomerRepository repository) {
		return new ApplicationRunner() {

			@Override
			public void run(ApplicationArguments args) throws Exception {
				var names = List.of("Josh Long", "Dave Syer", "Oliver Gierke", "Brian Cloze", "Sebastien Deleuz");
				names
				.stream()
				.map(Customer::new)
				.forEach(repository::save);
			}
		
		};
	}
		
}


@RestController
@RequiredArgsConstructor
class CustomerRestController {
	
	private final CustomerRepository repository;
	
	@GetMapping("/customers")
	Collection<Customer> get() {
		return repository.findAll();
	}
}

interface CustomerRepository extends JpaRepository<Customer, Integer> {
	
}

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
class Customer {
	
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	
	Customer(String name) {
		this.name = name;
	}
}

