package diplom.services;

import diplom.util.HTTPExecutor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.jni.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vova on 12.03.16.
 */
@Service
public class LoginService {

    @Autowired
    HttpClient httpClient;

    @Value("${http}")
    String http;

    @Autowired
    HTTPExecutor httpExecutor;

    private Map<String, String> cache = new ConcurrentHashMap<>();

    public void clearCache() {
        cache.clear();
    }

    public void remove(String key) {
        cache.remove(key);
    }

    public void add(String key, String value) {
        cache.put(key, value);
    }

    public String get(String key) {
        return cache.get(key);
    }

    public void checkCache(String sessionKey) {
        if (cache.get(sessionKey) != null)
            return;

        String result = httpExecutor.execute("/login/getlogin", "?sessionKey=" + sessionKey);
        if (result != null)
            cache.put(sessionKey, result);
    }

    public String getUsername(String sessionKey) {
        String username = cache.get(sessionKey);
        if (username != null)
            return username;
        while ((username = cache.get(sessionKey)) == null) {
            try {
                java.lang.Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
        return username;
    }
}
