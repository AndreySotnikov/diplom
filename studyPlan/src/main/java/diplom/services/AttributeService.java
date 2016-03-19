package diplom.services;

import diplom.entity.*;
import diplom.repository.AttributeRepository;
import diplom.repository.CharacteristicRepository;
import diplom.repository.FileRepository;
import diplom.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by vova on 12.03.16.
 */
@Service
public class AttributeService {

    @Autowired
    CharacteristicRepository characteristicRepository;

    @Autowired
    AttributeRepository attributeRepository;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    FileRepository fileRepository;


    public Characteristic addCharateristic(String value,
                                           String type,
                                           Operator operator,
                                           int attributeId,
                                           int fileId,
                                           int subId) {
        Attribute attribute = attributeRepository.findOne(attributeId);
        if (attribute == null)
            return null;
        File file = fileRepository.findOne(fileId);
        Subscription subscription = subscriptionRepository.findOne(subId);
        if (file == null && subscription == null)
            return null;
        Characteristic characteristic = new Characteristic();
        characteristic.setAttribute(attribute);
        characteristic.setValue(value);
        characteristic.setType(type);
        characteristic.setOperator(operator);
        characteristic.setcfile(file);
        characteristic.setSubscription(subscription);
        return characteristicRepository.save(characteristic);
    }

    public Attribute addAttribute(String name) {
        return attributeRepository.save(new Attribute(name));
    }
}
