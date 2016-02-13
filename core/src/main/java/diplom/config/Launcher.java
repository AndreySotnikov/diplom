package diplom.config;

import diplom.entity.Right;
import diplom.entity.RightType;
import diplom.entity.User;
import diplom.repository.RightRepository;
import diplom.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by Андрей on 13.02.2016.
 */
public class Launcher {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Config.class,args);
        UserRepository userRepository = context.getBean(UserRepository.class);
        userRepository.save(new User("login","name","pass"));

        RightRepository rightTypeRepository = context.getBean(RightRepository.class);
        rightTypeRepository.save(new Right(true,null,null, null, "123"));//RightType.DELETE.name()));

    }
}
