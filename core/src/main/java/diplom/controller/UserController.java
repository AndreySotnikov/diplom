package diplom.controller;

import diplom.entity.User;
import diplom.services.LoginService;
import diplom.services.UserService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created on 13.02.2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    LoginService loginService;

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

    @CrossOrigin(origins = {"http://localhost:8080","http://localhost:8081"},
            allowCredentials = "true",
            methods = {RequestMethod.POST,RequestMethod.GET})
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
    public void save(@RequestBody User user){
        userService.save(user);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public void update(@RequestBody User user, @PathVariable String id){
        userService.update(id, user);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public void delete(@PathVariable String id){
        userService.delete(id);
    }
}
