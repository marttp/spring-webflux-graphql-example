package dev.tpcoder.security;

import dev.tpcoder.security.model.Customer;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
class SecureGraphqlController {

  private final CrmService crmService;

  SecureGraphqlController(CrmService crmService) {
    this.crmService = crmService;
  }

  @QueryMapping
  public Mono<Customer> customerById(@Argument Integer id) {
    return this.crmService.getCustomerById(id);
  }

  @MutationMapping
  public Mono<Customer> insert(@Argument String name) {
    return this.crmService.insert(name);
  }
}
