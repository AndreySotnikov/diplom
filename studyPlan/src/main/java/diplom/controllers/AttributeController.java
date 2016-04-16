package diplom.controllers;

import diplom.entity.Attribute;
import diplom.repository.AttributeRepository;
import diplom.services.FileService;
import diplom.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by vova on 12.03.16.
 */
@RestController
@RequestMapping("/attributes")
public class AttributeController {

    @Value("${admin.name}")
    String fileDir;

    @Autowired
    AttributeRepository attributeRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam("sessionKey") String sessionKey,
                             @RequestParam("name") String name) {
        if (sessionKey == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        Attribute attribute = new Attribute(name);
        attributeRepository.save(attribute);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
