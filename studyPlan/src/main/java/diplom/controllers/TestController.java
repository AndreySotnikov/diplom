package diplom.controllers;

import diplom.util.JinqSource;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created on 27.02.2016.
 */
@RestController
@RequestMapping("/")
public class TestController {

    @Autowired
    HttpClient httpClient;

    @Value("${http}")
    String http;

    @Autowired
    JinqSource source;

    @PersistenceContext
    private EntityManager em;

    @RequestMapping("/all")
    public String test() throws IOException {

        HttpGet httpGet = new HttpGet("/user/all");

        String response = httpClient.execute(new HttpHost("127.0.0.1", 8082, http), httpGet,
                r -> {
                    HttpEntity entity = r.getEntity();
                    return EntityUtils.toString(entity);
                });

        return response;
    }

    @RequestMapping(value = "/getName", method = RequestMethod.GET)
    public String getNameByEntityId(@RequestParam("entityId") Integer entityId){
        return source.streamAll(em, diplom.entity.File.class)
                .where(f -> f.getEntityId() == entityId).findFirst().get().getName();
    }
}
