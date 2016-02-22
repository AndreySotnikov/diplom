package diplom.services;

import diplom.entity.User;
import diplom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 13.02.2016.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAll(){
        List<User> userList = new ArrayList<>();
        userRepository.findAll().forEach(userList::add);
        return userList;
    }

    public User findOne(String username){
        return userRepository.findOne(username);
    }

    public void save(User user){
        userRepository.save(user);
    }

    public void delete(String username){
        userRepository.delete(username);
    }

    public User update(String id,User user){
        User forUpdate = findOne(id);
        forUpdate.setName(user.getPassword());
        forUpdate.setPassword(user.getPassword());
        save(forUpdate);
        return forUpdate;
    }
}
