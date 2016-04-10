package diplom.controller;

import diplom.dto.UserGroupDTO;
import diplom.entity.Group;
import diplom.entity.User;
import diplom.repository.GroupRepository;
import diplom.services.AdminService;
import diplom.services.LoginService;
import diplom.services.UserService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created on 13.02.2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(){
        return "Karina<3";
    }

//    @ResponseBody
//    @RequestMapping(value = "/all", method = RequestMethod.GET)
//    public List<User> getAll(){
//        return userService.findAll();
//    }

//    @CrossOrigin(origins = {"http://localhost:8080","http://localhost:8081"},
//            allowCredentials = "true",
//            methods = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAll(@RequestParam("sessionKey") String sessionKey){
        Validate.notNull(sessionKey);
        String login = loginService.getLoginBySessionKey(sessionKey);
        if (login==null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://localhost:8080","http://localhost:8081"},
            allowCredentials = "true",
            methods = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    @RequestMapping(value = "/{sessionKey}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> findOne(@PathVariable String sessionKey){
        Validate.notNull(sessionKey);
        String login = loginService.getLoginBySessionKey(sessionKey);
        if (login==null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(userService.findOne(login), HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<Object> save(@RequestParam("login") String login,
                                         @RequestParam("name") String name,
                                         @RequestParam("email") String email,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("sessionKey") String sessionKey,
                                         @PathVariable String id){
        Validate.notNull(sessionKey);
        if (!adminService.checkAccess(sessionKey))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        User user = new User(login,name,email,phone);
        loginService.register(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestParam("login") String login,
                       @RequestParam("name") String name,
                       @RequestParam("email") String email,
                       @RequestParam("phone") String phone,
                       @RequestParam("sessionKey") String sessionKey,
                       @PathVariable String id){
        Validate.notNull(sessionKey);
        if (!adminService.checkAccess(sessionKey))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        User user = new User(login,name,email,phone);
        userService.update(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> delete(@PathVariable String id,
                       @RequestParam("sessionKey") String sessionKey){
        Validate.notNull(sessionKey);
        if (!adminService.checkAccess(sessionKey))
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/one/{userId}", method = RequestMethod.GET, produces = "application/json")
    @Transactional
    public ResponseEntity<Object> findUserGroups(@PathVariable String userId,
                                                 @RequestParam("sessionKey") String sessionKey){
        Validate.notNull(sessionKey);
        String login = loginService.getLoginBySessionKey(sessionKey);
        if (login==null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        List<UserGroupDTO> userGroups = new ArrayList<>();
        User user = userService.findOne(userId);
        List<Group> allGroups = new ArrayList<>();
        groupRepository.findAll().forEach(allGroups::add);
        List<Group> groups = user.getGroups();
        groups.forEach(g -> userGroups.add(new UserGroupDTO(){{setGroup(g); setEnabled(true);}}));
        allGroups.stream()
                .filter(g -> !groups.contains(g))
                .forEach(gr -> userGroups.add(new UserGroupDTO(){{setGroup(gr); setEnabled(false);}}));
        return new ResponseEntity<>(userGroups, HttpStatus.OK);
    }

    @RequestMapping(value = "/not-in-group", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getUsersNotInGroup(@RequestParam("sessionKey") String sessionKey,
                                                     @RequestParam("groupId") Integer groupId){
        Validate.notNull(sessionKey);
        List<User> users = adminService.getUsersNotInGroup(sessionKey,groupId);
        if (users == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
}
