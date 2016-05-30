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
        Attribute attribute = new Attribute("attribute", "root");
        attributeRepository.save(attribute);

        File file = new File("dir", "Marks", "Marks of 2010 year", false, 1);
        File file2 = new File("dir2", "Students", "Students of 3 course", true, 2);
        File file3 = new File("dir2", "Students", "Students of 3 course", true, 2);
        File file4 = new File("dir2", "Students", "Students of 3 course", true, 2);
        File file5 = new File("dir2", "Students", "Students of 3 course", true, 2);
        fileRepository.save(file);
        fileRepository.save(file2);
        fileRepository.save(file3);
        fileRepository.save(file4);
        fileRepository.save(file5);

        Revision revision = new Revision();
        revision.setDescription("Order");
        revision.setFile(file);
        revision.setUsername("root");
        revision.setPath(revision.getDescription());

        Revision revision3 = new Revision();
        revision3.setDescription("Order");
        revision3.setFile(file);
        revision3.setUsername("root");
        revision3.setPath(revision.getDescription());

        Revision revision4 = new Revision();
        revision4.setDescription("Order");
        revision4.setFile(file);
        revision4.setUsername("root");
        revision4.setPath(revision.getDescription());

        Revision revision5 = new Revision();
        revision5.setDescription("Order");
        revision5.setFile(file);
        revision5.setUsername("root");
        revision5.setPath(revision.getDescription());

        Revision revision2 = new Revision();
        revision2.setDescription("Students");
        revision2.setFile(file2);
        revision2.setUsername("root");
        revision2.setPath(revision2.getDescription());

        revisionRepository.save(revision);
        revisionRepository.save(revision2);
        revisionRepository.save(revision3);
        revisionRepository.save(revision4);
        revisionRepository.save(revision5);

        Characteristic characteristic = new Characteristic("val", "int",Operator.EQ, attribute, file, null);
        characteristicRepository.save(characteristic);

        Subscription subscription = new Subscription(file, true, "root");
        subscriptionRepository.save(subscription);

        subscription = new Subscription(file, true, "root");
        subscriptionRepository.save(subscription);

        subscription = new Subscription(file, true, "root");
        subscriptionRepository.save(subscription);

        subscription = new Subscription(file, true, "root");
        subscriptionRepository.save(subscription);

        subscription = new Subscription(file, true, "root");
        subscriptionRepository.save(subscription);


    }

    @Transactional
    public void init1(){
        Characteristic characteristic1 = characteristicRepository.findOne(1);
        Attribute attribute = attributeRepository.findOne(1);
        System.out.println(characteristic1);
    }
}
