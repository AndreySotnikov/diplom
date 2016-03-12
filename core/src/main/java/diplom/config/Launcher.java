package diplom.config;

import diplom.entity.*;
import diplom.repository.*;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by Андрей on 13.02.2016.
 */
public class Launcher {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Config.class,args);
        UserRepository userRepository = context.getBean(UserRepository.class);
        RightRepository rightRepository = context.getBean(RightRepository.class);
        GroupRepository groupRepository = context.getBean(GroupRepository.class);
        EntityRepository entityRepository = context.getBean(EntityRepository.class);
        RightTypeRepository rightTypeRepository = context.getBean(RightTypeRepository.class);
        EntityTypeRepository entityTypeRepository = context.getBean(EntityTypeRepository.class);
        ServiceRepository serviceRepository = context.getBean(ServiceRepository.class);
        UserServiceRepository userServiceRepository = context.getBean(UserServiceRepository.class);

        EntityType entityType = new EntityType("file");
        entityTypeRepository.save(entityType);

        Entity entity = new Entity(entityType);
        entityRepository.save(entity);
        RightType rightType = new RightType("write");
        rightTypeRepository.save(rightType);
        Group group = new Group("group");
        groupRepository.save(group);
        User user = new User("login","name","pass");
        userRepository.save(user);
        Service service = new Service("study", "descr");
        serviceRepository.save(service);
        serviceRepository.save(new Service("students", "descr"));
        userServiceRepository.save(new UserService(user,service,true));
        rightRepository.save(new Right(true,entity,group, user, rightType));

    }
}
