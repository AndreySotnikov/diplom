package diplom.services;

import diplom.entity.*;
import diplom.entity.UserService;
import diplom.repository.GroupRepository;
import diplom.repository.ServiceRepository;
import diplom.repository.UserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Value("${admin.name}")
    String adminName;

    private boolean checkAccess(String sessionKey) {
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

}
