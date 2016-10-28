package syvenko;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan(basePackages = "syvenko")
@RestController
@PropertySource("file:${app.conf}")
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext ctx = SpringApplication.run(App.class, args);
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}


