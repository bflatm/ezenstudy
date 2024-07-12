package practice.ezenstudy;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class AuthenticationConfig implements WebMvcConfigurer {

    private final LoginStudentResolver loginStudentResolver;

    public AuthenticationConfig(LoginStudentResolver loginStudentResolver) {
        this.loginStudentResolver = loginStudentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(loginStudentResolver);
    }
}
