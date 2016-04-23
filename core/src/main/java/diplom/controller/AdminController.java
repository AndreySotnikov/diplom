package diplom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import diplom.dto.RightsDTO;
import diplom.dto.UserGroupDTO;
import diplom.entity.Service;
import diplom.entity.User;
import diplom.entity.UserService;
import diplom.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created on 22.02.2016.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ObjectMapper objectMapper;

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

    @RequestMapping(value = "/group/rights/{groupId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getGroupRights(@PathVariable Integer groupId,
                                                 @RequestParam("sessionKey") String sessionKey){
        List<RightsDTO> rights = adminService.getGroupRights(sessionKey,groupId);
        if (rights == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(rights,HttpStatus.OK);
    }

    @RequestMapping(value = "/group/defaultrights/{groupId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getDefaultGroupRights(@PathVariable Integer groupId,
                                                 @RequestParam("sessionKey") String sessionKey){
        List<RightsDTO> rights = adminService.getDefaultGroupRights(sessionKey,groupId);
        if (rights == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(rights,HttpStatus.OK);
    }

    @RequestMapping(value = "/addUserToGroup", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> addUserToGroup(@RequestParam("sessionKey") String sessionKey,
                                                 @RequestParam("groupId") Integer groupId,
                                                 @RequestParam("users") String users) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User[] obj = mapper.readValue(users, User[].class);
        if (!adminService.addUserToGroup(sessionKey,groupId,obj))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/group/removeUser", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> removeUserFromGroup(@RequestParam("sessionKey") String sessionKey,
                                                      @RequestParam("groupId") Integer groupId,
                                                      @RequestParam("user") String userJson) throws IOException {
        User user = objectMapper.readValue(userJson, User.class);
        if (!adminService.removeUserFromGroup(sessionKey,groupId,user))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/groups/rights/active", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getActiveRightTypes(@RequestParam("sessionKey") String sessionKey,
                                                      @RequestParam("entityId") Integer entityId,
                                                      @RequestParam("groupId") Integer groupId){
        List<RightsDTO> allEntityGroupRights = null;
        if ((allEntityGroupRights = adminService.getActiveRightTypes(sessionKey,groupId,entityId)) == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(allEntityGroupRights,HttpStatus.OK);
    }

}
