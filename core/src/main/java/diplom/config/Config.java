package diplom.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by Андрей on 13.02.2016.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("diplom")
@PropertySource("classpath:application.properties")
@EntityScan("diplom")
@EnableJpaRepositories("diplom")
@EnableTransactionManagement
@EnableWebMvc
public class Config {
}
