package diplom.controller;

import diplom.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 22.02.2016.
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @CrossOrigin(origins = {"http://localhost:8080","http://localhost:8081"},
            allowCredentials = "true",
            methods = {RequestMethod.POST,RequestMethod.GET})
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public boolean register(@RequestParam("login") String username,
                            @RequestParam("token") String password,
                            @RequestParam(required = false, name = "name") String name,
                            @RequestParam(required = false, name = "email") String email,
                            @RequestParam(required = false, name = "phone") String phone) {
        return loginService.register(username, password, name, email, phone);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("login") String username,
                        @RequestParam("token") String password) {
        if (loginService.login(username, password))
            return loginService.nextSessionId(username);
        return "Fail";
    }
}
