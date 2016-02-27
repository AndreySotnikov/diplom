package diplom.controller;

import diplom.entity.User;
import diplom.services.LoginService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 22.02.2016.
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody User user) {

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam("login") String username,
                        @RequestParam("token") String password) {
        Validate.notNull(username, "Username is null");
        Validate.notNull(password, "Password is null");
        if (loginService.login(username, password))
            return loginService.nextSessionId(username);
        return null;
    }
}
