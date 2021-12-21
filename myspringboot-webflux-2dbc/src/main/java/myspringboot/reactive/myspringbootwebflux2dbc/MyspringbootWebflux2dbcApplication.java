package myspringboot.reactive.myspringbootwebflux2dbc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Spring WebFlux Crud App",
		version = "1.0",
		description = "Spring WebFlux Crud App Documentation"
))
public class MyspringbootWebflux2dbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyspringbootWebflux2dbcApplication.class, args);
	}

}
