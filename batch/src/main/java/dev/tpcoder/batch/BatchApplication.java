package dev.tpcoder.batch;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class BatchApplication {

  public static void main(String[] args) {
    SpringApplication.run(BatchApplication.class, args);
  }

}

@Controller
class BatchController {

  @QueryMapping
  Collection<Customer> customers() {
    return List.of(
        new Customer(1, "A"),
        new Customer(2, "B")
    );
  }

  @BatchMapping
  Map<Customer, Account> account(List<Customer> customers) {
    System.out.println("Calling account for " + customers.size() + " customers.");
    // Network call
    // Then mapping
    return customers
        .stream()
        .collect(Collectors.toMap(c -> c, c -> new Account(c.id())));
  }

//  @SchemaMapping(typeName = "Customer")
//  Account account(Customer customer) {
//    // Might be a network call
//    System.out.println("Getting account for customer #" + customer.id());
//    return new Account(customer.id());
//  }
}

record Customer(Integer id, String name) {

}

record Account(Integer id) {

}