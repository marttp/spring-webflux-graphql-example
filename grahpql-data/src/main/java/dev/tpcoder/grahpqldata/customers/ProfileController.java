package dev.tpcoder.grahpqldata.customers;

import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ProfileController {

  @SchemaMapping(typeName = "Customer")
  Profile profile(Customer customer) {
    return new Profile(customer.id());
  }
}
