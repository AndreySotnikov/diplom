package diplom.services;

import diplom.dto.RightDTO;
import diplom.dto.RightsDTO;
import diplom.dto.UserGroupDTO;
import diplom.entity.*;
import diplom.entity.UserService;
import diplom.repository.*;
import diplom.util.HTTPExecutor;
import diplom.util.JinqSource;
import org.aspectj.util.Reflection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by vova on 05.03.16.
 */
@Service
public class AdminService {

    @Autowired
    private LoginService loginService;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserServiceRepository userServiceRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private RightService rightService;

    @Autowired
    private RightRepository rightRepository;

    @Autowired
    private EntityRepository entityRepository;

    @Autowired
    private NewEntitiesRightsRepository newEntitiesRepository;

    @Autowired
    private RightTypeRepository rtRepository;

    @Autowired
    private diplom.services.UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityTypeRepository etRepository;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private JinqSource source;

    @Autowired
    private HTTPExecutor httpExecutor;

    @Value("${admin.name}")
    String adminName;

    public boolean checkAccess(String sessionKey) {
        String login = loginService.getLoginBySessionKey(sessionKey);
        return adminName != null && adminName.equals(login);
    }

    public boolean addService(String name, String description, String sessionKey) {
        if (!checkAccess(sessionKey))
            return false;
        serviceRepository.save(new diplom.entity.Service(name, description));
        return true;
    }

    public boolean setServiceUserEnabled(int serviceId, String userId,
                                      boolean enabled, String sessionKey) {
        if (!checkAccess(sessionKey))
            return false;
        List<UserService> userServices = userServiceRepository.getByIds(userId, serviceId);
        if (userServices.isEmpty()) {
            if (!enabled)
                return true;
            else
                userServiceRepository.save(new UserService(new User(userId),
                        new diplom.entity.Service(serviceId), enabled));
        }
        UserService userService = userServices.get(0);
        if (userService.isValue() == enabled)
            return true;
        userService.setValue(enabled);
        userServiceRepository.save(userService);
        return true;
    }

    public List<diplom.entity.Service> getUserServices(String sessionKey,String userId){
        if (!checkAccess(sessionKey))
            return null;
        return userServiceRepository.getServices(userId);
    }

    public List<diplom.entity.Service> getAllServices(String sessionKey){
        if (!checkAccess(sessionKey))
            return null;
        List<diplom.entity.Service> services = new ArrayList<>();
        serviceRepository.findAll().forEach(services::add);
        return services;
    }

    public List<User> getGroupUsers(String sessionKey, int groupId){
        if (!checkAccess(sessionKey))
            return null;
        return groupRepository.findOne(groupId).getUsers();
    }

    @Transactional
    public boolean updateUserGroups(String sessionKey, String login, UserGroupDTO[] groups){
        if (!checkAccess(sessionKey))
            return false;
        User user = userService.findOne(login);
        user.setGroups(Stream.of(groups)
                .filter(gr -> gr.isEnabled())
                .map(UserGroupDTO::getGroup)
                .collect(Collectors.toList()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean updateUserServices(String sessionKey, String login, UserService[] services){
        if (!checkAccess(sessionKey))
            return false;
        User user = userService.findOne(login);
        StreamSupport.stream(userServiceRepository.findAll().spliterator(), false)
                .filter(us -> us.getUser().equals(user))
                .forEach(userServiceRepository::delete);
        Stream.of(services).forEach(s -> {
            s.setUser(user);
            userServiceRepository.save(s);
        });
        return true;
    }

    @Transactional
    public List<User> getUsersNotInGroup(String sessionKey, int groupId){
        if (!checkAccess(sessionKey))
            return null;
        Group group = groupRepository.findOne(groupId);
        return source.streamAll(em, User.class)
                .where(u -> !(u.getGroups().contains(group)))
                .toList();
    }

    @Transactional
    public List<RightsDTO> getGroupRights(String sessionKey, int groupId){
        List<RightsDTO> rightsList = new ArrayList<>();
//        Map<String,List<String>> fileRights = new HashMap<>();
        if (!checkAccess(sessionKey))
            return null;
        List<Right> rigths = source.streamAll(em, Right.class)
                .where(right -> right.getGroup().getId() == groupId).toList();
        rigths.forEach(r -> {
            diplom.entity.Service service = r.getService();
            String address = service.getAddress();
            String host[] = address.split(":");
            httpExecutor.setHost(host[1].substring(2));
            httpExecutor.setProtocol(host[0]);
            httpExecutor.setPort(Integer.parseInt(host[2]));
            String name = httpExecutor.execute("/getName", "?entityId=" + r.getEntity().getId());
//            if (fileRights.get(name) == null)
//                fileRights.put(name, new ArrayList<>());
//            fileRights.get(name).add(r.getRightType().getName());
            RightsDTO foundRight = rightsList.stream().filter(rDto -> rDto.getName().equals(name))
                    .findFirst().orElseGet(() -> {
                        RightsDTO rightsDTO = new RightsDTO();
                        rightsDTO.setName(name);
//                        rightsDTO.getTypes().add(r.getRightType().getName());
                        rightsDTO.setEntityId(r.getEntity().getId());
                        rightsList.add(rightsDTO);
                        return rightsDTO;
                    });
            foundRight.getTypes().add(r.getRightType().getName());
//            if (foundRight == null) {
//                RightsDTO rightsDTO = new RightsDTO();
//                rightsDTO.setName(name);
//                rightsDTO.getTypes().add(r.getRightType().getName());
//                rightsDTO.setEntityId(r.getEntity().getId());
//                rightsList.add(rightsDTO);
//            }else
//                foundRight.getTypes().add(r.getRightType().getName());
        });
        return rightsList;
    }

    public List<RightsDTO> getDefaultGroupRights(String sessionKey, Integer groupId) {
        List<RightsDTO> rightsList = new ArrayList<>();
//        Map<String,List<String>> fileRights = new HashMap<>();
        if (!checkAccess(sessionKey))
            return null;
        String name = "Остальные";
        List<NewEntitiesRights> rigths = source.streamAll(em, NewEntitiesRights.class)
                .where(right -> right.getGroup().getId() == groupId).toList();
        rigths.forEach(r -> {
            RightsDTO foundRight = rightsList.stream().filter(rDto -> rDto.getName().equals(name))
                    .findFirst().orElseGet(() -> {
                        RightsDTO rightsDTO = new RightsDTO();
                        rightsDTO.setName(name);
//                        rightsDTO.getTypes().add(r.getRightType().getName());
                        rightsDTO.setEntityId(-1);
                        rightsList.add(rightsDTO);
                        return rightsDTO;
                    });
            foundRight.getTypes().add(r.getRightType().getName());
//            if (fileRights.get(name) == null)
//                fileRights.put(name, new ArrayList<>());
//            fileRights.get(name).add(r.getRightType().getName());
        });
        return rightsList;
    }

    @Transactional
    public boolean addUserToGroup(String sessionKey, Integer groupId, User[] obj) {
        if (!checkAccess(sessionKey))
            return false;
        Group group = groupRepository.findOne(groupId);
        Stream.of(obj).forEach(u -> {
            User user = userService.findOne(u.getLogin());
            user.getGroups().add(group);
            userRepository.save(user);
        });
        return true;
    }

    @Transactional
    public boolean removeUserFromGroup(String sessionKey, Integer groupId, User user) {
        if (!checkAccess(sessionKey))
            return false;
        User u = userRepository.findOne(user.getLogin());
        u.getGroups().remove(groupRepository.findOne(groupId));
        userRepository.save(u);
        return true;
    }

    @Transactional
    public List<RightsDTO> getActiveRightTypes(String sessionKey, Integer groupId, Integer entityId) {
        if (!checkAccess(sessionKey))
            return null;
        List<RightsDTO> allRights = new ArrayList<>();
        if (entityId != -1) {
            List<RightType> rightTypes = source.streamAll(em, RightType.class).toList();
            List<Right> rigths = source.streamAll(em, Right.class)
                    .where(right -> right.getGroup().getId() == groupId && right.getEntity().getId() == entityId).toList();
            RightsDTO rightsDto = new RightsDTO();
            rigths.forEach(r -> rightsDto.getTypes().add(r.getRightType().getName()));
            rightsDto.setEntityId(entityId);
            rightsDto.setEnabled(true);
            allRights.add(rightsDto);
            RightsDTO disabled = new RightsDTO();
            disabled.setEnabled(false);
            disabled.setEntityId(entityId);
            rightTypes.stream().filter(rt -> !rightsDto.getTypes().contains(rt.getName()))
                    .map(frt -> disabled.addType(frt.getName())).collect(Collectors.toList());
            allRights.add(disabled);
        }else{
            List<RightType> rightTypes = source.streamAll(em, RightType.class).toList();
            //TODO fix right type
            List<NewEntitiesRights> rigths = source.streamAll(em, NewEntitiesRights.class)
                    .where(right -> right.getGroup().getId() == groupId &&
                            right.getEntityType().getName().equals("file")).toList();
            RightsDTO rightsDto = new RightsDTO();
            rigths.forEach(r -> rightsDto.getTypes().add(r.getRightType().getName()));
            rightsDto.setEntityId(entityId);
            rightsDto.setEnabled(true);
            allRights.add(rightsDto);
            RightsDTO disabled = new RightsDTO();
            disabled.setEnabled(false);
            disabled.setEntityId(entityId);
            rightTypes.stream().filter(rt -> !rightsDto.getTypes().contains(rt.getName()))
                    .map(frt -> disabled.addType(frt.getName())).collect(Collectors.toList());
            allRights.add(disabled);
        }
        return allRights;
    }

    @Transactional
    public boolean updateActiveRightTypes(String sessionKey, RightDTO[] rightsArr, Integer groupId) {
        if (!checkAccess(sessionKey))
            return false;
        int entityId = rightsArr[0].getId();
        Group group = groupRepository.findOne(groupId);
        diplom.entity.Service service = source.streamAll(em, diplom.entity.Service.class)
                .where(s -> s.getName().equals("study")).findFirst().get();
        if (entityId!=-1) {
            source.streamAll(em, Right.class).where(r -> r.getGroup().getId() == groupId
                    && r.getEntity().getId() == entityId)
                    .forEach(r1 -> rightRepository.delete(r1.getId()));
        }else{
            source.streamAll(em, NewEntitiesRights.class).where(r -> r.getGroup().getId() == groupId
                    && r.getEntityType().getName().equals("file"))
                    .forEach(r1 -> newEntitiesRepository.delete(r1.getId()));
        }
        if (entityId!=-1)
            for(RightDTO rd : rightsArr){
                if (!rd.isEnabled())
                    continue;
                Right right = new Right();
                right.setValue(rd.isEnabled());
                Entity entity = entityRepository.findOne(entityId);
                right.setEntity(entity);
                right.setGroup(group);
                right.setService(service);
                RightType righttype = rtRepository.getRightTypeByName(rd.getType());
                right.setRightType(righttype);
                rightRepository.save(right);
            }
        else{
            for(RightDTO rd : rightsArr){
                if (!rd.isEnabled())
                    continue;
                NewEntitiesRights right = new NewEntitiesRights();
                right.setGroup(group);
                right.setService(service);
                EntityType type = source.streamAll(em,EntityType.class).where(e -> "file".equals(e.getName())).findFirst().get();
                right.setEntityType(type);
                RightType righttype = rtRepository.getRightTypeByName(rd.getType());
                right.setRightType(righttype);
                newEntitiesRepository.save(right);
            }
        }
        return true;
    }

    public boolean removeGroup(String sessionKey, Integer groupId) {
        if (!checkAccess(sessionKey))
            return false;
        Group gr = groupRepository.findOne(groupId);
        gr.getUsers().forEach(u -> {u.getGroups().remove(gr); userRepository.save(u);});
        groupRepository.delete(groupId);
        return true;
    }

    @Transactional
    public boolean createGroup(String sessionKey, String name, String descr) {
        if(!checkAccess(sessionKey))
            return false;
        Group gr = new Group();
        List<EntityType> lst = source.streamAll(em, EntityType.class).where(et -> et.getName().equals("file")).toList();
        List<RightType> rtLst = source.streamAll(em, RightType.class).toList();
        gr.setName(name);
        gr.setDescription(descr);
        groupRepository.save(gr);
        rtLst.forEach(rt -> {
            NewEntitiesRights ner = new NewEntitiesRights();
            ner.setGroup(gr);
            ner.setEntityType(lst.get(0));
            ner.setRightType(rt);
            newEntitiesRepository.save(ner);
        });
        return true;
    }

    @Transactional
    public boolean updateService(String sessionKey, int id, diplom.entity.Service service){
        if(!checkAccess(sessionKey))
            return false;
        diplom.entity.Service updatedService = serviceRepository.findOne(id);
        updatedService.setName(service.getName());
        updatedService.setDescription(service.getDescription());
        updatedService.setAddress(service.getAddress());
        serviceRepository.save(updatedService);
        return true;
    }

    @Transactional
    public boolean removeService(String sessionKey, int id){
        if(!checkAccess(sessionKey))
            return false;
        serviceRepository.delete(id);
        return true;
    }

    @Transactional
    public boolean createService(String sessionKey, String name, String description, String ipAddress){
        if(!checkAccess(sessionKey))
            return false;
        diplom.entity.Service service = new diplom.entity.Service(name, description, ipAddress);
        serviceRepository.save(service);
        return true;

    }
}
