package diplom.services;

import diplom.entity.Entity;
import diplom.entity.Group;
import diplom.entity.User;
import diplom.repository.EntityRepository;
import diplom.repository.NewEntitiesRightsRepository;
import diplom.repository.RightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 22.02.2016.
 */
@Service
public class RightService {
    @Autowired
    private RightRepository rightRepository;
    @Autowired
    private EntityRepository entityRepository;

    @Autowired
    private NewEntitiesRightsRepository newEntitiesRightsRepository;

    @Value("${admin.name}")
    String adminName;

    public boolean checkRights(String username, int entityId, String rightType){
        Entity entity = entityRepository.findOne(entityId);
        return checkRights(username,entity,rightType);
    }

    public boolean checkRights(String username, Entity entity, String rightType){
        if (username == null || entity == null || rightType == null)
            return false;
        if (username.equals(adminName))
            return true;
        if (newEntitiesRightsRepository.checkAccessToEntityType(
                username,entity.getEntityType().getName(), rightType)!=0)
            return true;
        if (rightRepository.checkAccessToEntity(
                username,entity.getId(), rightType)!=0)
            return true;
        List<Group> groups = newEntitiesRightsRepository.checkGroupAccessToEntityType(
                entity.getEntityType().getName(),rightType);

        for(Group g : groups)
            for (User u : g.getUsers())
                if (u.getLogin().equals(username))
                    return true;

        groups = rightRepository.checkGroupAccessToEntity(entity.getId(),rightType);
        for(Group g : groups)
            for (User u : g.getUsers())
                if (u.getLogin().equals(username))
                    return true;

        return false;
    }

}
