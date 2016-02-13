package diplom.services;

import diplom.entity.User;
import diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 13.02.2016.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> getAll(){
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        return userList;
    }
}
