package diplom.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import diplom.util.HTTPExecutor;
import diplom.util.SubscriptionEvent;
import diplom.util.SubscriptionListener;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;

/**
 * Created on 07.05.2016.
 */
@Service
public class SubscriptionService implements InitializingBean{

    @Autowired
    private FileService fileService;

    @Autowired
    private HTTPExecutor httpExecutor;

    @Override
    public void afterPropertiesSet() throws Exception {
        fileService.addListener(new InternalListener());
    }

    public class InternalListener implements SubscriptionListener{

        @Override
        public void handle(SubscriptionEvent event) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                event.setUsers(Arrays.asList(new String[]{"root"}));
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
