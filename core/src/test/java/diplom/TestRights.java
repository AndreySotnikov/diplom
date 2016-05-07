package diplom;

import diplom.config.Config;
import diplom.entity.*;
import diplom.repository.*;
import diplom.services.MailService;
import diplom.services.RightService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created on 22.02.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Config.class)
@TestPropertySource(locations="classpath:application.properties")
public class TestRights {

    @Autowired
    private RightService rightService;
    @Autowired
    private EntityRepository entityRepository;
    @Autowired
    private EntityTypeRepository entityTypeRepository;
    @Autowired
    private RightTypeRepository rightTypeRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private NewEntitiesRightsRepository entitiesRightsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RightRepository rightRepository;

    @Autowired
    private MailService mailService;

    @Test
    public void testMail(){
        mailService.builder()
                .text("test mail")
                .subject("test")
                .from("vsuammsystem@gmail.com")
                .to("100kov1994@gmail.com")
                .send();
    }


    @Test
    @Transactional
    public void checkRights() throws Exception{
        EntityType entityType = new EntityType("file");
        entityTypeRepository.save(entityType);

        Entity entity = new Entity(entityType);
//        entityRepository.save(entity);

        RightType rightType = new RightType("write");
        rightTypeRepository.save(rightType);

        User user1 = new User("login1", "name1", "pass1");
        User user2 = new User("login2", "name2", "pass2");
        User user3 = new User("login3", "name3", "pass3");
        User user4 = new User("login4", "name4", "pass4");
        User user5 = new User("login5", "name5", "pass5");
        Stream.of(user1,user2,user3,user4,user5).forEach(userRepository::save);

        Group group1 = new Group("group1", Stream.of(user1,user2,user3).collect(Collectors.toList()));
        Group group2 = new Group("group2", Stream.of(user3,user4,user5).collect(Collectors.toList()));
        Stream.of(group1,group2).forEach(groupRepository::save);

        entitiesRightsRepository.save(new NewEntitiesRights(entityType,
                group1, null, rightType));

        assertTrue(rightService.checkRights("login2",entity, "write"));
        assertTrue(rightService.checkRights("login3",entity, "write"));
        assertFalse(rightService.checkRights("login5",entity, "write"));
    }

    @Test
    public void test() throws Exception{
        Map<String, String> map = new HashMap<>();
        map.put(null, "Karina");
        System.out.println(map.get(null));
    }

    @Test
    public void testSignature() throws Exception {
        final String message = "Hello world is a stupid message to be signed";

        final KeyPair keyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();

        final Signature privSig = Signature.getInstance("SHA1withRSA");
        privSig.initSign(keyPair.getPrivate());
        privSig.update(message.getBytes());

        byte[] signature = privSig.sign();

        final Signature pubSig = Signature.getInstance("SHA1withRSA");

        pubSig.initVerify(keyPair.getPublic());
        pubSig.update(message.getBytes());

        assertTrue(pubSig.verify(signature));
    }
}
