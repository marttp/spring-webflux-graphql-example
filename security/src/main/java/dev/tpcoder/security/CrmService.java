package dev.tpcoder.security;

import dev.tpcoder.security.model.Customer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import reactor.core.publisher.Mono;

@Service
class CrmService {

  private final Map<Integer, Customer> db = new ConcurrentHashMap<>();
  private final AtomicInteger id = new AtomicInteger();

  @Secured("ROLE_USER")
  public Mono<Customer> getCustomerById(Integer id) {
    var customer = this.db.get(id);
    if (ObjectUtils.isEmpty(customer)) {
      return Mono.empty();
    }
    return Mono.just(customer);
  }

  @PreAuthorize("hasRole('ADMIN')")
  public Mono<Customer> insert(String name) {
    var newCustomer = new Customer(this.id.incrementAndGet(), name);
    this.db.put(this.id.get(), newCustomer);
    return Mono.just(newCustomer);
  }
}
