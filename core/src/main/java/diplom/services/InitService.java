package diplom.services;

import diplom.entity.*;
import diplom.entity.UserService;
import diplom.repository.*;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Created on 20.04.2016.
 */
@Service
public class InitService implements InitializingBean{

    @Autowired
    EntityTypeRepository entityTypeRepository;
    @Autowired
    EntityRepository entityRepository;
    @Autowired
    RightTypeRepository rightTypeRepository;
    @Autowired
    LoginService loginService;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    ServiceRepository serviceRepository;
    @Autowired
    UserServiceRepository userServiceRepository;
    @Autowired
    RightRepository rightRepository;
    @Autowired
    NewEntitiesRightsRepository newEntitiesRightsRepository;

    public void init(){
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
        loginService.register(user);
        group.setUsers(new ArrayList<User>(){{add(user);}});
        groupRepository.save(group);
        diplom.entity.Service service = new diplom.entity.Service("study", "descr");
        service.setAddress("https://127.0.0.1:8081");
        serviceRepository.save(service);
        serviceRepository.save(new diplom.entity.Service("students", "descr"));
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

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }
}
