package myspringboot.reactive.myspringbootwebflux2dbc.controller;

import myspringboot.reactive.myspringbootwebflux2dbc.dto.Customer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class CustomerWebClient {
    private static WebClient webClient;

    @BeforeAll
    public static void setWebClient() {
        webClient = WebClient.create("http://localhost:8080");
    }

    @Test
    public void getAllCustomerStream() {
        Flux<Customer> customerFlux = webClient.get().uri("/customers/stream")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(Customer.class);

        StepVerifier.create(customerFlux)
                .expectNextCount(10)
                .verifyComplete();
    }
}
