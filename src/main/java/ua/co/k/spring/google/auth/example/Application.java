package ua.co.k.spring.google.auth.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import pl.allegro.tech.boot.autoconfigure.handlebars.HandlebarsAutoConfiguration;

@SpringBootApplication(exclude = {
        HandlebarsAutoConfiguration.class
})
@Slf4j
public class Application {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler("/");

        http.authorizeRequests()
                .antMatchers("/")
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .failureHandler((request, response, exception) -> {
                    request.getSession().setAttribute("error.message", exception.getMessage());
                    handler.onAuthenticationFailure(request, response, exception);
                })
                .loginPage("/")
                .and()
                .logout().logoutSuccessUrl("/")
        ;
        return http.build();
    }
    
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    
    
    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        log.info("App started!");
    }
}
