package pl.wit.ilog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import pl.wit.ilog.internals.config.AppProperties;
import pl.wit.ilog.internals.config.DatabaseConfig;

@Import(DatabaseConfig.class)
@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class WitApplication {

    public static void main(String[] args) {
        SpringApplication.run(WitApplication.class, args);
    }

}
