package diplom.services;

import diplom.dto.UserGroupDTO;
import diplom.entity.*;
import diplom.entity.UserService;
import diplom.repository.GroupRepository;
import diplom.repository.ServiceRepository;
import diplom.repository.UserRepository;
import diplom.repository.UserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
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
}
