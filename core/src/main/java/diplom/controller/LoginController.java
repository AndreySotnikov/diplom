package diplom.controller;

import diplom.entity.User;
import diplom.services.LoginService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 22.02.2016.
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public void register(@RequestBody User user) {
//
//    }

    @CrossOrigin(origins = {"http://localhost:8080","http://localhost:8081"},
            allowCredentials = "true",
            methods = {RequestMethod.POST,RequestMethod.GET})
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public boolean register(@RequestParam("login") String username,
                            @RequestParam("token") String password,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        Validate.notNull(username, "Username is null");
        Validate.notNull(password, "Password is null");
//        response.setHeader("Access-Control-Allow-Origin",request.getHeader("origin"));
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT" );
        return loginService.register(username, password);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("login") String username,
                        @RequestParam("token") String password) {
        Validate.notNull(username, "Username is null");
        Validate.notNull(password, "Password is null");
        if (loginService.login(username, password))
            return loginService.nextSessionId(username);
        return null;
    }
}
