package io.jzheaux.springsecurity.resolutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
public class ResolutionsApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ResolutionsApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .mvcMatchers(HttpMethod.GET, "/resolutions", "/resolution/**")
                        .hasAuthority("resolution:read")
                        .anyRequest().hasAuthority("resolution:write"))
                .httpBasic(basic -> {
                });
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository users) {
        return new UserRepositoryUserDetailsService(users);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
                org.springframework.security.core.userdetails.User
                        .withUsername("user")
                        .password("{bcrypt}$2a$10$ehmyrSSSbhBWdrXmRc5//uk6azXEibmtGVlO/DRec/zxS9XmQUPrW")
                        .authorities("resolution:read")
                        .build());
    }
}
