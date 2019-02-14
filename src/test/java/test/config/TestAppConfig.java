package test.config;

import configuration.DefaultAppConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "test/utils")
@Import(DefaultAppConfig.class)
public class TestAppConfig {
}
