package dev.tpcoder.queries;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class QueriesApplication {

  public static void main(String[] args) {
    SpringApplication.run(QueriesApplication.class, args);
  }

}


@Controller
class GreetingController {

  @QueryMapping
//  @SchemaMapping(typeName = "Query", field = "hello")
  Mono<String> hello() {
    String txt = "Hello World";
    return Mono.just(txt);
  }

  @QueryMapping
  String helloWithName(@Argument String name) {
    return "Hello " + name;
  }

  @QueryMapping
  Customer customerById(@Argument Integer id) {
    return new Customer(id, Math.random() > .5 ? "A" : "B");
  }

  private final List<Customer> customerList = List.of(
      new Customer(1, "A"),
      new Customer(2, "B")
  );

  @QueryMapping
//  Collection<Customer> customers() {
//    return customerList;
//  }
  Flux<Customer> customers() {
    return Flux.fromIterable(this.customerList);
  }

  @SchemaMapping(typeName = "Customer")
  Mono<Account> account(Customer customer) {
    return Mono.just(new Account(customer.id()));
  }

  // region RSocket
  @QueryMapping
  Greeting greeting() {
    return new Greeting("Hello, World!");
  }

  @SubscriptionMapping
  Flux<Greeting> greetings() {
    return Flux
        .fromStream(Stream.generate(() -> new Greeting("Hello, world @ " + ZonedDateTime.now() + "!")))
        .delayElements(Duration.ofSeconds(1))
        .take(10);
  }
  // endregion
}

record Customer(Integer id, String name) {

}

record Account(Integer id) {

}

record Greeting(String greeting) {

}