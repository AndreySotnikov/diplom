package diplom.services;

import diplom.entity.*;
import diplom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 22.02.2016.
 */
@Service
@Scope("singleton")
public class TestService {
    @Autowired
    FileRepository fileRepository;
    @Autowired
    AttributeRepository attributeRepository;
    @Autowired
    CharacteristicRepository characteristicRepository;
    @Autowired
    RevisionRepository revisionRepository;
    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Transactional
    public void init(){
        Attribute attribute = new Attribute("attr");
        attributeRepository.save(attribute);

        File file = new File("dir", "name", "desc", false, 1);
        fileRepository.save(file);

        Revision revision = new Revision();
        revision.setDescription("i added this");
        revision.setFile(file);
        revision.setUsername("username");
        revision.setPath(file.getName());

        revisionRepository.save(revision);

        Characteristic characteristic = new Characteristic("val", "int",Operator.EQ, attribute, file, null);
        characteristicRepository.save(characteristic);
        fileRepository.findOne(1).getCharacteristics();

        Subscription subscription = new Subscription(file, true, "root");
        subscriptionRepository.save(subscription);

    }

    @Transactional
    public void init1(){
        Characteristic characteristic1 = characteristicRepository.findOne(1);
        Attribute attribute = attributeRepository.findOne(1);
        System.out.println(characteristic1);
    }
}
