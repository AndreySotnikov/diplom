package diplom.services;

import diplom.util.HTTPExecutor;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public String getLoginBySession(String sessionKey) {
        if (sessionKey == null)
            return null;

        if (cache.get(sessionKey) != null)
            return cache.get(sessionKey);

        String result = httpExecutor.execute("/getlogin", "?sessionKey=" + sessionKey);
        if (result != null)
            cache.put(sessionKey, result);
        return cache.get(sessionKey);
    }

}
