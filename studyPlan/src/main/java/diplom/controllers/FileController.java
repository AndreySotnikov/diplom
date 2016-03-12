package diplom.controllers;

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

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by vova on 12.03.16.
 */
@RestController
@RequestMapping("/test")
public class FileController {

    @Value("${admin.name}")
    String fileDir;

    @Autowired
    LoginService loginService;

    @Autowired
    FileService fileService;



    @RequestMapping(value = "/uploadfile", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam("sessionKey") String sessionKey,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("fileid") int fileid,
                             @RequestParam("descr") String descr) {
        if (sessionKey == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        loginService.checkCache(sessionKey);
        if (fileService.fullSave(file, fileid, loginService.getUsername(sessionKey), descr))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);

    }

}
