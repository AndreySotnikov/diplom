package diplom.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by vova on 19.03.16.
 */
@Service
public class HTTPExecutor {

    @Autowired
    private static HttpClient httpClient;

    @Value("${http}")
    private static String http;

    private static String host = "127.0.0.1";

    public static String execute(String url, String params) {
        HttpGet httpGet = new HttpGet(url + params);
        String result = null;
        try {
            result = httpClient.execute(new HttpHost("127.0.0.1", 8082, http),
                    httpGet,
                    r -> {
                        return EntityUtils.toString(r.getEntity());
                    });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
