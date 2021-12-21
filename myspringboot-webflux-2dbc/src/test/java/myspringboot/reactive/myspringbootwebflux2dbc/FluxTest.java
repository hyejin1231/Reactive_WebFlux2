package myspringboot.reactive.myspringbootwebflux2dbc;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    public void justFlux() {
        Flux<String> stringFlux = Flux.just("Hello", "WebFlux").log();
        stringFlux.subscribe(val -> System.out.println("val = " + val));

        // stepVerifier 사용
        // 선언적 프로그래밍(Declarative Programming)의 단점으로 테스트하기 어렵다는 점이 꼽히고 있습니다.
        // 이를 보완하기 위해 Reactor에서는 StepVerifier라는 Flux/Mono 테스트 도구를 제공하고 있습니다.
        StepVerifier.create(stringFlux)
//                .expectNext("Hello")
//                .expectNext("WebFlux")
                .expectNextCount(2)
                .verifyComplete();
//                .expectComplete()
//                .verify();
    }

    @Test
    public void errorFlux() {
        Flux<String> flux = Flux.just("Boot", "MSA")
                .concatWithValues("Cloud")
                .concatWith(Flux.error(new RuntimeException("Exception 발생됨!!")))
                .concatWithValues("Reactive mono")
                .log();

        flux.subscribe(System.out::println, (e) -> System.out.println(e.getMessage()));

        StepVerifier.create(flux)
                .expectNext("Boot")
                .expectNext("MSA")
                .expectNext("Cloud")
                .expectError(RuntimeException.class)
                .verify();

    }

    @Test
    void subscribeFlux() {
        Flux<String> stringFlux = Flux.just("Hello", "WebFlux", "Boot");
        stringFlux.subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription subscription) {
                subscription.request(Integer.MAX_VALUE);
//                subscription.request(1);
            }

            @Override
            public void onNext(String s) {
                System.out.println("FluxTest.onNext " + s);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("FluxTest.onError " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("FluxTest.onComplete ");
            }
        });

        StepVerifier.create(stringFlux)
                .expectNext("Hello")
                .expectNext("WebFlux")
                .expectNext("Boot")
                .expectComplete();
    }

    @Test
    public void rangeFlux() {
        Flux<Integer> integerFlux = Flux.range(10, 10)
                .filter(num -> Math.floorMod(num, 2) == 1)
                .log();

        integerFlux.subscribe(System.out::println);

        StepVerifier.create(integerFlux)
                .expectNext(11, 13, 15, 17, 19)
                .verifyComplete();
    }
}
