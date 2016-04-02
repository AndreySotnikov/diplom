package diplom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import diplom.dto.UserGroupDTO;
import diplom.entity.Service;
import diplom.entity.User;
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
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
                .sorted((us1, us2) -> us1.getName().compareTo(us2.getName()))
                .forEachOrdered(service -> userServices.add(new UserService(null,service,false)));
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

    @RequestMapping(value = "/updateGroups", method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<Object> updateGroups(@RequestParam("sessionKey") String sessionKey,
                                         @RequestParam("login") String login,
                                         @RequestParam("groups") String groups) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserGroupDTO[] obj = mapper.readValue(groups, UserGroupDTO[].class);
        if (adminService.updateUserGroups(sessionKey,login,obj))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/updateServices", method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<Object> updateServices(@RequestParam("sessionKey") String sessionKey,
                                         @RequestParam("login") String login,
                                         @RequestParam("services") String services) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserService[] obj = mapper.readValue(services, UserService[].class);
        if (adminService.updateUserServices(sessionKey,login,obj))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/group/one/{groupId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getGroupUsers(@PathVariable Integer groupId,
                                                @RequestParam("sessionKey") String sessionKey){
        List<User> users = adminService.getGroupUsers(sessionKey, groupId);
        if (users == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
