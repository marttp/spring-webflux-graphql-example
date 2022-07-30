package dev.tpcoder.grahpqldata.customers;

import org.springframework.data.querydsl.ReactiveQuerydslPredicateExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository // Use this if not Java Engine
public interface CustomerRepository extends ReactiveCrudRepository<Customer, String>,
    ReactiveQuerydslPredicateExecutor<Customer> {

}
