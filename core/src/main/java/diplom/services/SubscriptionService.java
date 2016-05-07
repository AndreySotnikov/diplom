package diplom.services;

import diplom.entity.User;
import diplom.repository.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created on 07.05.2016.
 */
@Service
public class SubscriptionService implements InitializingBean{


    private Queue<SubscriptionRequest> subRequests = new LinkedList<>();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @Scheduled(fixedRate = 600000)
    public void startThread() {
        System.err.println("Scheduler invoked");
        while (!subRequests.isEmpty()) {
            SubscriptionRequest sr = subRequests.poll();
            String action;
            switch (sr.getEventType()) {
                case "revision_update":
                    action = "обновил ревизию файла";
                    break;
                default:
                    action = "изменил файл";
            }
            String text = String.format("Пользователь %s %s %s %tD", sr.getProducer().getName(),
                    action, sr.entityName, sr.getDate());
            sr.getSubs().forEach(s -> {
                if (s.getEmail() != null)
                    mailService.builder().subject("File updated").to(s.getEmail()).text(text).send();
            });
        }
    }

    public void addSubscription(String entity, String userLogin, String type, String[] usersLst) {
        User user = userRepository.findOne(userLogin);
        List<User> subs = new ArrayList<>();
        for (String u : usersLst){
            subs.add(userRepository.findOne(u));
        }
        SubscriptionRequest sr = new SubscriptionRequest();
        sr.setEventType(type);
        sr.setProducer(user);
        sr.setSubs(subs);
        sr.setEntityName(entity);
        sr.setDate(new Date());
        subRequests.add(sr);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        startThread();
    }

    public class SubscriptionRequest {
        private String eventType;
        private String entityName;
        private User producer;
        private List<User> subs;
        private Date date;

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getEntityName() {
            return entityName;
        }

        public void setEntityName(String entityName) {
            this.entityName = entityName;
        }

        public User getProducer() {
            return producer;
        }

        public void setProducer(User producer) {
            this.producer = producer;
        }

        public List<User> getSubs() {
            return subs;
        }

        public void setSubs(List<User> subs) {
            this.subs = subs;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }
    }
}
