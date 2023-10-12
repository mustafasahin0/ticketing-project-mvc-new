package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.RoleService;
import com.cydeo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl extends AbstractMapService<UserDTO, String> implements UserService {

    RoleService roleService;

    public UserServiceImpl(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public UserDTO save(UserDTO object) {
        return super.save(object.getUserName(), object);
    }

    @Override
    public List<UserDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public void update(UserDTO object) {
        super.update(object.getUserName(), object);
    }

    @Override
    public UserDTO findById(String id) {
        return super.findById(id);
    }

    public List<UserDTO> managers() {
        return findAll().stream().filter(each -> each.getRole().equals(roleService.findById(2L))).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> employees() {
        return findAll().stream().filter(each -> each.getRole().equals(roleService.findById(3L))).collect(Collectors.toList());
    }
}
