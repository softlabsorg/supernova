package shared.config.framework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"stepdefinitions", "domains", "shared"})
public class SpringConfig {
}