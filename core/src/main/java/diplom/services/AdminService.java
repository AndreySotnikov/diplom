package diplom.services;

import diplom.entity.*;
import diplom.entity.UserService;
import diplom.repository.ServiceRepository;
import diplom.repository.UserServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by vova on 05.03.16.
 */
@Service
public class AdminService {

    @Autowired
    LoginService loginService;

    @Autowired
    ServiceRepository serviceRepository;

    @Autowired
    UserServiceRepository userServiceRepository;

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
        List<UserService> userServices = userServiceRepository.getByIds(serviceId, userId);
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



}
