package rest.restApi.service;

import rest.restApi.dao.RoleDao;
import rest.restApi.dao.UserDao;
import rest.restApi.model.Role;
import rest.restApi.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDAO, RoleDao roleDAO, PasswordEncoder passwordEncoder) {
        this.userDao = userDAO;
        this.roleDao = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User getById(long id) {
        User user = null;
        Optional<User> optional = userDao.findById(id);
        if(optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public void save(User user) {
        userDao.save(passwordCoder(user));
    }

    @Override
    public void update(User user) {
        userDao.save(user);
    }

    @Override
    public void deleteById(long id) {
        userDao.deleteById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    @PostConstruct
    public void addDefaultUser() {
        Set<Role> roles1 = new HashSet<>();
        roles1.add(roleDao.findById(1L).orElse(null));
        Set<Role> roles2 = new HashSet<>();
        roles2.add(roleDao.findById(1L).orElse(null));
        roles2.add(roleDao.findById(2L).orElse(null));
        User user = new User("Marie","Smith",(byte) 25, "user@mail.com", "user","12345",roles1);
        User admin = new User("Edvard","Blair",(byte) 30, "admin@mail.com", "admin","admin",roles2);
        save(user);
        save(admin);
        }
}

