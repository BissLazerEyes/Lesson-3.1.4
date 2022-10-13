package rest.restApi.service;

import rest.restApi.dao.RoleDao;
import rest.restApi.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService{

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDAO) {
        this.roleDao = roleDAO;
    }

    @Override
    public List<Role> findAllRole() {
        return roleDao.findAll();
    }

    @Override
    @PostConstruct
    public void addDefaultRole() {
        roleDao.save(new Role("ROLE_USER"));
        roleDao.save(new Role("ROLE_ADMIN"));
    }

    @Override
    public Set<Role> findByIdRoles(List<Long> roles) {
      return new HashSet<>(roleDao.findAllById(roles));
    }
}
