package diplom.services;

import diplom.entity.User;
import diplom.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 22.02.2016.
 */
@Service
@Scope("singleton")
public class LoginService {

    private Map<String,String> sessionKeys = new ConcurrentHashMap<>();

    @Autowired
    private UserRepository userRepository;

    private static SecureRandom random = new SecureRandom();

    public boolean register(User user){
        if (userRepository.findOne(user.getLogin()) != null)
            return false;
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean register(String login, String password, String name, String email, String phone){
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        user.setName(name);
        return register(user);
    }

    public boolean login(String login, String pass){
        User user = userRepository.findOne(login);
        if (user == null || pass == null)
            return false;
        if (user.getPassword().equals(DigestUtils.md5Hex(pass)))
            return true;
        return false;
    }

    public String nextSessionId(String login) {
        String sessionKey = new BigInteger(130, random).toString(32);
        sessionKeys.put(sessionKey,login);
        return sessionKey;
    }

    public String getLoginBySessionKey(String sessionKey){
        return sessionKeys.get(sessionKey);
    }
}
