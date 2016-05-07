package diplom.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import diplom.entity.Subscription;
import diplom.util.HTTPExecutor;
import diplom.util.JinqSource;
import diplom.util.SubscriptionEvent;
import diplom.util.SubscriptionListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created on 07.05.2016.
 */
@Service
public class SubscriptionService implements InitializingBean{

    @Autowired
    private FileService fileService;

    @Autowired
    private HTTPExecutor httpExecutor;

    @Autowired
    private JinqSource source;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void afterPropertiesSet() throws Exception {
        fileService.addListener(new InternalListener());
    }

    public class InternalListener implements SubscriptionListener{

        @Override
        public void handle(SubscriptionEvent event) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<String> us = source.streamAll(em, Subscription.class)
                        .where(s-> s.getFile().getName().equals(event.getEntityName()))
                        .map(Subscription::getLogin)
                        .collect(Collectors.toList());
                event.setUsers(us);
                String users = mapper.writeValueAsString(event.getUsers());
                String encoded = URLEncoder.encode(users, "UTF-8");
                httpExecutor.execute("/subscription/send",
                        "?file=" + event.getEntityName()
                                + "&user=" + event.getLogin()
                                + "&type=" + event.getEventType()
                                + "&users=" + encoded);
            }catch (Exception e){
                //Expected behavior
            }
        }
    }
}
