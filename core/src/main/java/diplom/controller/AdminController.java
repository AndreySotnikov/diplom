package diplom.controller;

import diplom.entity.Service;
import diplom.entity.UserService;
import diplom.services.AdminService;
import diplom.services.LoginService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 22.02.2016.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/service/add", method = RequestMethod.POST)
    public boolean addService(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("sessionKey") String sessionKey) {
        return adminService.addService(name, description, sessionKey);
    }

    @RequestMapping(value = "/service-user/enable", method = RequestMethod.POST)
    public boolean setUserServiceEnaled(@RequestParam("userId") String userId,
                                 @RequestParam("serviceId") int serviceId,
                                 @RequestParam("sessionKey") String sessionKey,
                                 @RequestParam("enabled") boolean enabled) {
        return adminService.setServiceUserEnabled(serviceId, userId, enabled, sessionKey);
    }

    @RequestMapping(value = "service-user/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getUserServices(@RequestParam("sessionKey") String sessionKey,
                                                  @RequestParam("userId") String userId){
        List<UserService> userServices = new ArrayList<>();
        List<Service> allServices = adminService.getAllServices(sessionKey);
        List<Service> services = adminService.getUserServices(sessionKey, userId);
        services.forEach(s -> userServices.add(new UserService(null,s,true)));
        allServices.stream()
                .filter(s -> !services.contains(s))
                .forEach(service -> userServices.add(new UserService(null,service,false)));
        if (services == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(userServices, HttpStatus.OK);
    }

    @RequestMapping(value = "service/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllServices(@RequestParam("sessionKey") String sessionKey){
        List<Service> services = adminService.getAllServices(sessionKey);
        if (services == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }
}
