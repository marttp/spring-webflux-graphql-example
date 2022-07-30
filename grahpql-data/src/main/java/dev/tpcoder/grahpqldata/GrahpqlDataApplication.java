package dev.tpcoder.grahpqldata;

import dev.tpcoder.grahpqldata.customers.Customer;
import dev.tpcoder.grahpqldata.customers.CustomerRepository;
import dev.tpcoder.grahpqldata.customers.QCustomer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.graphql.data.query.QuerydslDataFetcher;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class GrahpqlDataApplication {

  public static void main(String[] args) {
    SpringApplication.run(GrahpqlDataApplication.class, args);
  }

  /* Implement with Java Engine
  @Bean
  RuntimeWiringConfigurer runtimeWiringConfigurer(CustomerRepository repository) {
    return builder -> {
      var data = QuerydslDataFetcher.builder(repository).many();
      var datum = QuerydslDataFetcher.builder(repository).single();
      builder.type("Query", wiring -> wiring
          .dataFetcher("customer", datum)
          .dataFetcher("customers", data));
    };
  }
  */

  @Bean
  ApplicationRunner applicationRunner(CustomerRepository repository) {
    return args -> {
      Flux<Customer> allExceptStartWithM = repository
          .findAll(QCustomer.customer.name.startsWith("M").not());

      Flux<Customer> customerFlux = Flux.just("Mart", "Daniel", "Matha", "Arthur", "Joe", "Dave", "Marcus", "Omarr")
          .map(n -> new Customer(null, n));

      repository.deleteAll()
          .thenMany(customerFlux.flatMap(repository::save))
          .thenMany(allExceptStartWithM)
          .subscribe(System.out::println);
    };
  }
}

