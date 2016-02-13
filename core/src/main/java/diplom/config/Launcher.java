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
        SubscriptionRepository subscriptionRepository = context.getBean(SubscriptionRepository.class);
        RightTypeRepository rightTypeRepository = context.getBean(RightTypeRepository.class);
        EntityTypeRepository entityTypeRepository = context.getBean(EntityTypeRepository.class);

        EntityType entityType = new EntityType("file");
        entityTypeRepository.save(entityType);

        Entity entity = new Entity(entityType);
        entityRepository.save(entity);
        RightType rightType = new RightType("write");
        subscriptionRepository.save(new Subscription("email", entity, true));
        rightTypeRepository.save(rightType);
        Group group = new Group("group");
        groupRepository.save(group);
        User user = new User("login","name","pass");
        userRepository.save(user);
        rightRepository.save(new Right(true,entity,group, user, rightType));

    }
}
