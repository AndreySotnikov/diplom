package diplom.services;

import diplom.entity.User;
import diplom.repository.UserRepository;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 22.02.2016.
 */
@Service
public class LoginService {
    @Autowired
    UserRepository userRepository;

    public boolean register(User user){
        if (userRepository.findOne(user.getLogin()) != null)
            return false;
        userRepository.save(user);
        return true;
    }
}
