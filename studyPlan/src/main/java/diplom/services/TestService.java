package diplom.services;

import diplom.entity.Attribute;
import diplom.entity.Characteristic;
import diplom.entity.File;
import diplom.entity.Operator;
import diplom.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

        File file = new File("dir", "name", "desc", false);
        fileRepository.save(file);

        Characteristic characteristic = new Characteristic("val", "int",Operator.EQ, attribute, file, null);
        characteristicRepository.save(characteristic);
        fileRepository.findOne(1).getCharacteristics();
        System.out.println(attributeRepository.findOne(1).getCharacteristics());


    }

    @Transactional
    public void init1(){
        Characteristic characteristic1 = characteristicRepository.findOne(1);
        Attribute attribute = attributeRepository.findOne(1);
        System.out.println(characteristic1);
    }
}
