package shared.config.spring;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = SpringConfig.class)
public class CucumberSpringConfiguration {
}