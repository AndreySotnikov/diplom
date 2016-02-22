package diplom.controller;

import diplom.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 22.02.2016.
 */
@RestController
public class LoginController {
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void login(@RequestBody User user){

    }
}
