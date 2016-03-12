package diplom.controller;

import diplom.services.LoginService;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

/**
 * Created on 22.02.2016.
 */
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Value("${admin.name}")
    String fileDir;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> register(@RequestParam("login") String username,
                            @RequestParam("token") String password,
                            @RequestParam(required = false, name = "name") String name,
                            @RequestParam(required = false, name = "email") String email,
                            @RequestParam(required = false, name = "phone") String phone) {
        if (loginService.register(username, password, name, email, phone))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> login(@RequestParam("login") String username,
                                    @RequestParam("token") String password) {
        Validate.notNull(username, "Username is null");
        Validate.notNull(password, "Password is null");
        if (loginService.login(username, password))
            return new ResponseEntity<>(Collections.singletonMap("result",
                    loginService.nextSessionId(username)), HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/getlogin/{key}", method = RequestMethod.GET)
    public String getLoginBySessionKey(@PathVariable String key) {
        return loginService.getLoginBySessionKey(key);
    }


}
