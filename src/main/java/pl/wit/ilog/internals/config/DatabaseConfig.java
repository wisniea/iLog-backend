package pl.wit.ilog.internals.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EntityScan( basePackages = "pl.wit.ilog" )
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "pl.wit.ilog")
public class DatabaseConfig {

}
