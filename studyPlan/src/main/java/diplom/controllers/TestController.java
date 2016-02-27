package diplom.controllers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

/**
 * Created on 27.02.2016.
 */
@RestController
@RequestMapping("/test")
public class TestController {

//    @Autowired
//    RestTemplate restTemplate;

    @Autowired
    HttpClient httpClient;

    @RequestMapping("/all")
    public String test() throws IOException {

        HttpGet httpGet = new HttpGet("/user/all");

        String response = httpClient.execute(new HttpHost("127.0.0.1",8082,"https"),httpGet,
                r -> {
                    HttpEntity entity = r.getEntity();
                    return EntityUtils.toString(entity);
                });
        return response;
    }
}
