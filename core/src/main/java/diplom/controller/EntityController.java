package diplom.controller;

import diplom.entity.Entity;
import diplom.entity.Right;
import diplom.entity.RightType;
import diplom.repository.*;
import diplom.services.LoginService;
import diplom.services.RightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by vova on 19.03.16.
 */
@RestController
@RequestMapping("/entity")
public class EntityController {

    @Autowired
    RightService rightService;

    @Autowired
    EntityRepository entityRepository;

    @Autowired
    EntityTypeRepository entityTypeRepository;

    @Autowired
    RightRepository rightRepository;

    @Autowired
    RightTypeRepository rightTypeRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginService loginService;

    private static final int EVERYTHING_RIGHT_TYPE = 5;

    @RequestMapping(value = "/allfiles", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllFiles(@RequestParam("sessionKey") String sessionKey){
        List<Integer> result = rightService.getFilesToShow(sessionKey);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/addfile", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> addFile(@RequestParam("sessionKey") String sessionKey){
        if (sessionKey == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Entity entity = new Entity();
        entity.setEntityType(entityTypeRepository.findOne(0));
        int result = entityRepository.save(entity).getId();
        RightType rightType = rightTypeRepository.findOne(EVERYTHING_RIGHT_TYPE);
        Right right = new Right();
        right.setEntity(entity);
        right.setRightType(rightType);
        right.setUser(userRepository.findOne(loginService.getLoginBySessionKey(sessionKey)));
        right.setValue(true);
        rightRepository.save(right);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
