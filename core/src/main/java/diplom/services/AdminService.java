package diplom.services;

import diplom.dto.UserGroupDTO;
import diplom.entity.*;
import diplom.entity.UserService;
import diplom.repository.GroupRepository;
import diplom.repository.ServiceRepository;
import diplom.repository.UserRepository;
import diplom.repository.UserServiceRepository;
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
    private diplom.services.UserService userService;

    @Autowired
    private UserRepository userRepository;

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
    public Map<String,List<String>> getGroupRights(String sessionKey, int groupId){
        Map<String,List<String>> fileRights = new HashMap<>();
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
            if (fileRights.get(name) == null)
                fileRights.put(name, new ArrayList<>());
            fileRights.get(name).add(r.getRightType().getName());
        });
        return fileRights;
    }

    public Map getDefaultGroupRights(String sessionKey, Integer groupId) {
        Map<String,List<String>> fileRights = new HashMap<>();
        if (!checkAccess(sessionKey))
            return null;
        String name = "Остальные";
        List<NewEntitiesRights> rigths = source.streamAll(em, NewEntitiesRights.class)
                .where(right -> right.getGroup().getId() == groupId).toList();
        rigths.forEach(r -> {
            if (fileRights.get(name) == null)
                fileRights.put(name, new ArrayList<>());
            fileRights.get(name).add(r.getRightType().getName());
        });
        return fileRights;
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

}
