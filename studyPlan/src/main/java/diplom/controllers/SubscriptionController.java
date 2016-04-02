package diplom.controllers;

import diplom.repository.SubscriptionRepository;
import diplom.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by vova on 12.03.16.
 */
@RestController
@RequestMapping("/subs")
public class SubscriptionController {

    @Value("${admin.name}")
    String fileDir;

    @Autowired
    LoginService loginService;

    @Autowired
    SubscriptionRepository subscriptionRepository;


    @RequestMapping(value = "/all", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<Object> getAllFIles(@RequestParam("sessionKey") String sessionKey) {
        String login = loginService.getLoginBySession(sessionKey);
        if (login == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(subscriptionRepository.getByLogin(login), HttpStatus.OK);
    }

}
