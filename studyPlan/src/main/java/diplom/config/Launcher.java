package diplom.config;

import diplom.services.TestService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created on 22.02.2016.
 */
public class Launcher {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Config.class,args);
        context.getBean(TestService.class).init();
        context.getBean(TestService.class).init1();
    }
}
