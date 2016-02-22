package diplom.controller;

import diplom.services.RightService;
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

    @RequestMapping(value = "/checkAccess", method = RequestMethod.GET)
    public boolean checkAccess(@RequestParam("username") String username,
                               @RequestParam("idEntity") int idEntity,
                               @RequestParam("rightType") String rightType){
        return rightService.checkRights(username,idEntity,rightType);
    }
}
