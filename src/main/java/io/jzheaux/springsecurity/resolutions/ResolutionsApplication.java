package io.jzheaux.springsecurity.resolutions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class ResolutionsApplication extends WebSecurityConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(ResolutionsApplication.class, args);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authz -> authz
                        .anyRequest().authenticated())
                .httpBasic(basic -> {
                })
                .cors(cors -> {
                });
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository users) {
        return new UserRepositoryUserDetailsService(users);
    }

    @Bean
    WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        //.maxAge(0) // if using local verification, so that the browser doesn't cache any CORS preflight responses while you are making changes throughout the module
                        .allowedOrigins("http://localhost:4000")
                        .allowedMethods("HEAD")
                        .allowedHeaders("Authorization");
            }
        };
    }
}
