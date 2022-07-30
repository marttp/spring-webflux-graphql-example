package dev.tpcoder.mutations;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@SpringBootApplication
public class MutationsApplication {

  public static void main(String[] args) {
    SpringApplication.run(MutationsApplication.class, args);
  }

}

@Controller
class MutationsController {

  // In-Memory DB
  private final Map<Integer, Customer> db = new ConcurrentHashMap<>();
  // In-Memory PK (ID)
  private final AtomicInteger id = new AtomicInteger();

  @QueryMapping
  Collection<Customer> customers() {
    return db.values();
  }

  @QueryMapping
  Customer customerById(@Argument Integer id) {
    return this.db.getOrDefault(id, null);
  }

  @MutationMapping
//  @SchemaMapping(typeName = "Mutation", field = "addCustomer")
  Customer addCustomer(@Argument String name) {
    var id = this.id.incrementAndGet();
    var value = new Customer(id, name);
    db.put(id, value);
    return this.db.get(id);
  }
}

record Customer(Integer id, String name) {}