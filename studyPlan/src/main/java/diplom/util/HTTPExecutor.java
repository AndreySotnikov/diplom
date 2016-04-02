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
    private HttpClient httpClient;

    private static String host = "127.0.0.1";

    public String execute(String url, String params) {
        HttpGet httpGet = new HttpGet(url + params);
        String result = null;
        try {
            result = httpClient.execute(new HttpHost(host, 8082, "https"),
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
