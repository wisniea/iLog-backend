package pl.wit.ilog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import pl.wit.ilog.config.DatabaseConfig;

@Import(DatabaseConfig.class)
@EnableAutoConfiguration
@SpringBootApplication
public class WitApplication {

    public static void main(String[] args) {
        SpringApplication.run(WitApplication.class, args);
    }

}
