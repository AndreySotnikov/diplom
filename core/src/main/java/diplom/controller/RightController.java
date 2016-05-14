package diplom.controller;

import diplom.services.LoginService;
import diplom.services.RightService;
import diplom.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 22.02.2016.
 */
@RestController
@RequestMapping("/rights")
public class RightController {

    @Autowired
    RightService rightService;

    @Autowired
    LoginService loginService;

    @RequestMapping(value = "/checkAccess", method = RequestMethod.GET)
    public String checkAccess(@RequestParam("sessionKey") String sessionKey,
                               @RequestParam("entityId") int idEntity,
                               @RequestParam("rightType") String rightType){
        String username = loginService.getLoginBySessionKey(sessionKey);
        return String.valueOf(rightService.checkRights(username,idEntity,rightType));
    }
}
