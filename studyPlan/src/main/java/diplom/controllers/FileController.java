package diplom.controllers;

import diplom.entity.Revision;
import diplom.services.FileService;
import diplom.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;

/**
 * Created by vova on 12.03.16.
 */
@RestController
@RequestMapping("/files")
public class FileController {

    @Value("${dir}")
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
        loginService.getLoginBySession(sessionKey);
        if (fileService.fullSave(file, fileid, loginService.getLoginBySession(sessionKey),
                descr, sessionKey))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/uploadnewfile", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam("sessionKey") String sessionKey,
                                     @RequestParam("file") MultipartFile file,
                                     @RequestParam("descr") String descr,
                                     @RequestParam("name") String name,
                                     @RequestParam("attrs") String attrs) {
        if (sessionKey == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        String login = loginService.getLoginBySession(sessionKey);
        if (fileService.fullSaveFromScracth(file, file.getName(), login, sessionKey, descr, attrs, name))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<Object> getAllFIles(@RequestParam("sessionKey") String sessionKey) {
        if (sessionKey == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(fileService.getFilesForUser(sessionKey), HttpStatus.OK);
    }

    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<Object> deleteFile(@RequestParam("sessionKey") String sessionKey,
                                             @RequestParam("fileId") int fileId) {
        if (sessionKey == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(fileService.deleteFile(sessionKey, fileId), HttpStatus.OK);
    }

    @RequestMapping(value = "/getRevisions", method = RequestMethod.GET, produces="application/json")
    public ResponseEntity<Object> getRevs(@RequestParam("sessionKey") String sessionKey,
                                             @RequestParam("fileId") int fileId) {
        if (loginService.getLoginBySession(sessionKey) == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(fileService.getRevisions(fileId), HttpStatus.OK);
    }

    @RequestMapping(value = "/uploadnewrevision", method = RequestMethod.POST)
    public ResponseEntity uploadFile(@RequestParam("sessionKey") String sessionKey,
                                     @RequestParam("file") MultipartFile file,
                                     @RequestParam("descr") String descr,
                                     @RequestParam("fileid") int fileId) {
        if (loginService.getLoginBySession(sessionKey) == null)
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        if (fileService.addNewRevision(file, sessionKey, descr, fileId))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> downloadFile(
            @RequestParam("sessionKey") String sessionKey,
            @RequestParam("revId") int revId) {
        try {
            if (loginService.getLoginBySession(sessionKey) == null)
                return null;
            Revision revision = fileService.getRevision(revId);
            File file = new File(fileDir + revision.getPath());
            InputStream is = new FileInputStream(file);
            return ResponseEntity.created(new URI(""))
                    .header("Content-Disposition", "attachment; filename=\"" + revision.getFile().getName() + "\"")
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(is));
        } catch (Throwable e) {
            return null;
        }
    }

    @RequestMapping(value = "/fileSearch", method = RequestMethod.GET)
    public ResponseEntity fileSearch(
            @RequestParam("sessionKey") String sessionKey,
            @RequestParam("attrs") String attrs) {
        try {
            if (loginService.getLoginBySession(sessionKey) == null)
                throw new Exception("Auth Fail");
            return new ResponseEntity<>(fileService.searchFiles(attrs), HttpStatus.OK);
        } catch (Throwable e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
