package diplom.controller;

import diplom.entity.User;
import diplom.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 13.02.2016.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAll(){
        return userService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "/one/{id}", method = RequestMethod.GET)
    public User findOne(@PathVariable String id){
        return userService.findOne(id);
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
