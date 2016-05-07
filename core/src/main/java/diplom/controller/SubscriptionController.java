package diplom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import diplom.services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created on 07.05.2016.
 */
@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SubscriptionService subscriptionService;

    @RequestMapping("/send")
    public ResponseEntity addSubscriptionRequest(@RequestParam("file") String file,
                                                 @RequestParam("user") String user,
                                                 @RequestParam("type") String type,
                                                 @RequestParam("users") String users) throws IOException {
        String[] usersLst = objectMapper.readValue(users, String[].class);
        System.err.println(String.format("file=%s; user=%s; type=%s",file,user,type));
        subscriptionService.addSubscription(file,user,type,usersLst);
        return ResponseEntity.ok().build();
    }
}
