package myspringboot.reactive.myspringbootwebflux2dbc;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static java.util.stream.Collectors.toList;

public class LambdaTest {

    /*
        Stream의 map() 과 flatMap의 차이점 이해
     */
    @Test
    public void transformUsingStream() {
        List<Customer> customers = List.of(
                new Customer(101, "john", "john@gmail.com", Arrays.asList("397937955", "21654725")),
                new Customer(102, "smith", "smith@gmail.com", Arrays.asList("89563865", "2487238947")),
                new Customer(103, "peter", "peter@gmail.com", Arrays.asList("38946328654", "3286487236")),
                new Customer(104, "kely", "kely@gmail.com", Arrays.asList("389246829364", "948609467"))
        );


        // 이메일 주소 목록
        List<String> emailList = customers.stream()
                .map(cust -> cust.getEmail())
                .collect(toList());

        emailList.forEach(System.out::println);

        customers.stream()
                .map(Customer::getEmail)
                .collect(toList());

        // map() : <R> Stream <R> map(Function<? super T, ? extends R> mapper)
        List<List<String >> phoneList = customers.stream()
                .map(cust -> cust.getPhoneNumbers())
                .collect(toList());
        System.out.println("phoneList = " + phoneList);

        // flatmap() : <R> Stream <R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
        List<String> phoneList2 = customers.stream()
                .flatMap(customer -> customer.getPhoneNumbers().stream()) // Stream(Sttream<List<String>>
                .collect(toList());
        System.out.println("phoneList2 = " + phoneList2);

    }

    @Test
    public  void lambdaTest(){
         // Function lInterface 함수형 인터페이스가 가진 추상메서드를 재정의할 때 람다식으로 작성하기
        // 1. Anonymous Inner Class
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Annoymous Inner Class");
            }
        });
        t1.start();

        // 2. Lambda Expression
        Thread t2 = new Thread(() -> System.out.println("Lambda Expression"));
        t2.start();


        /*
            java.util.function 에 제공하는 함수형 인터페이스
            Consumer - void accept(T t)
            Predicate - boolean test(T t) 반드시 리턴타입이 boolean (필터링할 때 보통 사용)
            Supplier - T get()
            Function - R apply(T t)
            Operator -
                UnaryOperator R apply(T t)
                BinaryOperator R apply(T t, U u)
         */
        List<String> stringList = List.of("abc", "java", "boot");
        stringList.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("S = " +    s);
            }
        });

        // Lambda Expression
        stringList.forEach(val -> System.out.println(val));
        // Method Reference
        stringList.forEach(System.out::println);

    }
}
























