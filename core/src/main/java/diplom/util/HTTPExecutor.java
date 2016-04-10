package diplom.util;

import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created on 10.04.2016.
 */
@Service
public class HTTPExecutor {

    private String host;
    private int port;
    private String protocol;

    @Autowired
    private HttpClient httpClient;

    public String execute(String url, String params) {
        HttpGet httpGet = new HttpGet(url + params);
        String result = null;
        try {
            result = httpClient.execute(new HttpHost(host, port, protocol),
                    httpGet,
                    r -> {
                        return EntityUtils.toString(r.getEntity());
                    });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
