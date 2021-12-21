package myspringboot.reactive.myspringbootwebflux2dbc;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class MonoTest {

    @Test
    void justMono() {
        // Mono는 Publisher<T>의 구현체이다.
        // Flux와 다른 점은 0개 혹은 1개의 데이터만을 방출할 수 있다는 것이다.
        Mono<String> stringMono = Mono.just("Welcome to Webflux")
                .map(msg -> msg.concat(".com")).log();

        stringMono.subscribe(System.out::println);

        StepVerifier.create(stringMono)
                .expectNext("Welcome to Webflux.com")
                .verifyComplete();
    }

    @Test
    void errorMono() {
        Mono<String> errorMono = Mono.error(new RuntimeException("Check Error Mono"));

        errorMono.subscribe(
            value -> {
                System.out.println("onNext " + value);
            }, error -> {
                    System.out.println(error.getMessage());
                },
                ()-> {
                    System.out.println("OnComplete");
                }
        );

        StepVerifier.create(errorMono)
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    void fromSupplier() {
        Mono<String> stringMono = Mono.fromSupplier(() -> "Supplier Message").log();
    }


}
