package diplom.controller;

import diplom.services.AdminService;
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
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/service/add", method = RequestMethod.POST)
    public boolean addService(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("sessionKey") String sessionKey) {
        return adminService.addService(name, description, sessionKey);
    }


    @RequestMapping(value = "/service-user/enable", method = RequestMethod.POST)
    public boolean setUserServiceEnaled(@RequestParam("userId") String userId,
                                 @RequestParam("serviceId") int serviceId,
                                 @RequestParam("sessionKey") String sessionKey,
                                 @RequestParam("enabled") boolean enabled) {
        return adminService.setServiceUserEnabled(serviceId, userId, enabled, sessionKey);
    }

}
