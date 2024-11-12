package bitcamp.myapp;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    public SecurityConfig() {
        System.out.println("시큐리티 컨피그 호출됨");
    }
}
