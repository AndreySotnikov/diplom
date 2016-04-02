package diplom.controller;

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

    @RequestMapping(value = "/allfiles", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getAllServices(@RequestParam("sessionKey") String sessionKey){
        List<Integer> result = rightService.getFilesToShow(sessionKey);
        if (result == null)
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
