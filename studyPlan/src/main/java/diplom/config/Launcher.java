package diplom.config;

import diplom.entity.Attribute;
import diplom.entity.Characteristic;
import diplom.entity.File;
import diplom.entity.Operator;
import diplom.services.TestService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import diplom.repository.*;

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
