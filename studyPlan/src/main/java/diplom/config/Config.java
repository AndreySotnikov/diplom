package diplom.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

/**
 * Created on 22.02.2016.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("diplom")
@PropertySource("classpath:application.properties")
@EntityScan("diplom")
@EnableJpaRepositories("diplom")
@EnableTransactionManagement
@EnableWebMvc
public class Config {

    @Bean
    public ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public HttpClient getHttpClient(){
        try {
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(
                    createSslCustomContext(),
                    new String[]{"TLSv1.2"},
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());
            HttpClient client = HttpClients.custom().setSSLSocketFactory(csf).build();
            return client;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static SSLContext createSslCustomContext() throws Exception {
        // Trusted CA keystore
        KeyStore tks = KeyStore.getInstance("JKS");
        tks.load(Config.class.getClassLoader().getResourceAsStream("keys/ca.jks"), "111111".toCharArray());

        // Client keystore
        KeyStore cks = KeyStore.getInstance("JKS");
        cks.load(Config.class.getClassLoader().getResourceAsStream("keys/client.jks"), "111111".toCharArray());

        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(tks, new TrustSelfSignedStrategy()) // use it to customize
                .loadKeyMaterial(cks, "111111".toCharArray()) // load client certificate
                .build();
        return sslcontext;
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowCredentials(true)
                        .allowedMethods("GET", "PUT", "POST", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }

}
