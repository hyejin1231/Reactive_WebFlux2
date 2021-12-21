package myspringboot.reactive.myspringbootwebflux2dbc;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class FluxMapFltatTest {

    List<Customer> customerList = List.of(new Customer("gildong", "gildong@gmail.com"),
            new Customer("dooly", "dooly@gmail.com"));

    @Test
    public void transformUsingMap() {
       // public final <V> Flux <V> map (Function<? super T, ? extends V> mapper)
        Flux<Customer> customerFlux = Flux.fromIterable(customerList)
                .map(customer -> new Customer(customer.getName().toUpperCase(), customer.getEmail().toUpperCase()))
                .log();

        customerFlux.subscribe(System.out::println);

        StepVerifier.create(customerFlux)
                .expectNext(new Customer("GILDONG", "GILDONG@GMAIL.COM"))
                .expectNextCount(1)
                .verifyComplete();

    }

    @Test
    public void transformUsingFlatMap() {

        Flux<Customer> customerFlux = Flux.fromIterable(customerList)
                .flatMap(getFunction())
                .log();

        customerFlux.subscribe(System.out::println);

        StepVerifier.create(customerFlux)
                .expectNext(new Customer("GILDONG", "GILDONG@GMAIL.COM"))
                .expectNext(new Customer("DOOLY", "DOOLY@GMAIL.COM"))
                .verifyComplete();
    }

    private Function<Customer, Publisher<? extends Customer>> getFunction() {
        return customer -> Mono.just(new Customer(customer.getName().toUpperCase(), customer.getEmail().toUpperCase()));
    }

    @Test
    public void flatMapZipWithTest() {
        List<String> stringList = Arrays.asList("Olivia",
                "Emma",
                "Ava",
                "Charlotte",
                "Sophia",
                "Amelia",
                "Isabella",
                "Mia",
                "Evelyn");

        Flux<Integer> range = Flux.range(1, Integer.MAX_VALUE);

        // 1개으 글자로 쪼개서, sort, distinct, line 번호와 낱글자 출력
        Flux.fromIterable(stringList)
                // word.split("")의 리턴타입 Array String[]
                .flatMap(word -> Flux.fromArray(word.split("")))
                .sort()
                .distinct()
                .zipWith(range, (word, line) -> line + " = " + word)
                .subscribe(System.out::println);
    }



}
