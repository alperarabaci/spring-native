package com.training.reactive;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

/**
 * Started SpringNativeDemo2ReactiveApplication in 1.503 seconds (JVM running for 1.959)
 * 
 * mvn clean spring-boot:build-image
 * [INFO] ------------------------------------------------------------------------
	[INFO] BUILD SUCCESS
	[INFO] ------------------------------------------------------------------------
	[INFO] Total time:  06:32 min
 * 
 * docker run -p 8080:8080 docker.io/library/spring-native-demo-2-reactive:0.0.1-SNAPSHOT
 * Started SpringNativeDemo2ReactiveApplication in 0.068 seconds (JVM running for 0.071)
 * 
 * Second compile after minor change:
 * 
 * 
 [INFO] ------------------------------------------------------------------------
 [INFO] BUILD SUCCESS
 [INFO] ------------------------------------------------------------------------
 [INFO] Total time:  03:21 min
 [INFO] Finished at: 2021-04-26T23:41:32+03:00
 [INFO] ------------------------------------------------------------------------
 * 
 * 
 * TOO LONG!
 * 
 * @author alper
 *
 */
@SpringBootApplication
public class SpringNativeDemo2ReactiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringNativeDemo2ReactiveApplication.class, args);
	}
	
	@Bean
	ApplicationRunner runner(DatabaseClient dbc, CustomerRepository repository) {
		return new ApplicationRunner() {

			@Override
			public void run(ApplicationArguments args) throws Exception {
				
				var ddl = dbc.sql("create table customer(id serial primary key, name varchar(255) not null)").fetch().rowsUpdated();
				
				var writes = Flux.just("Josh Long", "Dave Syer", "Oliver Gierke", "Brian Cloze", "Sebastien Deleuz", "Alper Arabacı")
				.map(Customer::new)
				.flatMap(repository::save);
				
				var all = repository.findAll();
				
				ddl.thenMany(writes).thenMany(all).subscribe(System.out::println);
				
			}
		
		};
	}

}


@RestController
@RequiredArgsConstructor
class CustomerRestController {
	
	private final CustomerRepository repository;
	
	@GetMapping("/customers")
	Flux<Customer> get() {
		return repository.findAll();
	}
}

interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
	
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Customer {
	
	@Id
	private Integer id;
	private String name;
	
	Customer(String name) {
		this.name = name;
	}
}
