package diplom.config;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by Андрей on 13.02.2016.
 */
public class Launcher {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Config.class,args);
    }
}
