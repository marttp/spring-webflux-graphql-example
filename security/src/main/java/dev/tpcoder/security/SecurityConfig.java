package dev.tpcoder.security;

import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityWebFilterChain authorization(ServerHttpSecurity httpSecurity) {
    return httpSecurity
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(ae -> ae.anyExchange().permitAll())
        .httpBasic(Customizer.withDefaults())
        .build();
  }

  @Bean
  MapReactiveUserDetailsService authentication() {
    var users = Map.of("mart", new String[]{"USER"},
        "admin", "ADMIN,USER".split(","));
    // Header: Authorization: Basic YWRtaW46cHc=
    var userDetailList = users.entrySet()
        .stream()
        .map(e -> User.withDefaultPasswordEncoder()
            .username(e.getKey())
            .password("pw")
            .roles(e.getValue()).build()
        )
        .toList();
    return new MapReactiveUserDetailsService(userDetailList);
  }
}
