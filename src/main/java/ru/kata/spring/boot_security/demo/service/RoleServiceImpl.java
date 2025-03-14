package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAllRoles();
    }

    @Override
    public Role findRoleById(Long id) {
        return roleDao.findRoleById(id);
    }

    @Override
    public void addRole(Role role) {
        roleDao.addRole(role);

    }

    @Override
    public Set<Role> findRolesByIds(List<Long> roleIds) {
        return roleDao.findRolesByIds(roleIds);
    }
}
