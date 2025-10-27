package shared.config.framework;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = SpringConfig.class)
public class CucumberSpringConfiguration {
}