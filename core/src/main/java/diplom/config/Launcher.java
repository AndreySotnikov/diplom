package diplom.config;

import diplom.entity.*;
import diplom.repository.*;
import diplom.services.LoginService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.stream.Stream;

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
        NewEntitiesRightsRepository newEntitiesRightsRepository = context.getBean(NewEntitiesRightsRepository.class);
        LoginService loginService = context.getBean(LoginService.class);

        EntityType entityType = new EntityType("file");
        entityTypeRepository.save(entityType);

        Entity entity = new Entity(entityType);
        Entity entity2 = new Entity(entityType);
        entityRepository.save(entity);
        entityRepository.save(entity2);
        RightType rightType = new RightType("write");
        RightType rightType2 = new RightType("read");
        RightType rightType3 = new RightType("delete");
        RightType rightType4 = new RightType("update");
        RightType rightType5 = new RightType("grant/revoke");
        RightType rightType6 = new RightType("everything");
        rightTypeRepository.save(rightType);
        rightTypeRepository.save(rightType2);
        rightTypeRepository.save(rightType3);
        rightTypeRepository.save(rightType4);
        rightTypeRepository.save(rightType5);
        rightTypeRepository.save(rightType6);
        Group group = new Group("group");
        User user = new User("root","root","root");
        user.setEmail("100kov1994@gmail.com");
        loginService.register(user);
        group.setUsers(new ArrayList<User>(){{add(user);}});
        groupRepository.save(group);
        Service service = new Service("study", "descr");
        service.setAddress("https://127.0.0.1:8081");
        serviceRepository.save(service);
        serviceRepository.save(new Service("students", "descr"));
        userServiceRepository.save(new UserService(user,service,true));
        Right r = new Right(true,entity,group, null, rightType);
        r.setService(service);
        Right r1 = new Right(true,entity,group, null, rightType2);
        r1.setService(service);
        Right r2 = new Right(true,entity2,group, null, rightType2);
        r2.setService(service);
        rightRepository.save(r1);
        rightRepository.save(r2);
        rightRepository.save(r);
        NewEntitiesRights nr = new NewEntitiesRights(entityType,group,null, rightType);
        NewEntitiesRights nr1 = new NewEntitiesRights(entityType,group,null, rightType2);
        NewEntitiesRights nr2 = new NewEntitiesRights(entityType,group,null, rightType3);
        NewEntitiesRights nr3 = new NewEntitiesRights(entityType,group,null, rightType4);
        NewEntitiesRights nr4 = new NewEntitiesRights(entityType,group,null, rightType5);
        NewEntitiesRights nr5 = new NewEntitiesRights(entityType,group,null, rightType6);
        Stream.of(nr,nr1,nr2,nr3, nr4, nr5).forEach(newEntitiesRightsRepository::save);
        Right right = new Right(true, entity, null, user, rightType2);
    }
}
