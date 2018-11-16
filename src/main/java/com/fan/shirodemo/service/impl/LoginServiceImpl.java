package com.fan.shirodemo.service.impl;

import com.fan.shirodemo.entity.Permission;
import com.fan.shirodemo.entity.Role;
import com.fan.shirodemo.entity.User;
import com.fan.shirodemo.repository.RoleRepository;
import com.fan.shirodemo.repository.UserRepository;
import com.fan.shirodemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName LoginServiceImpl
 * @Description TODO
 * @Author Administrator
 * @Date 2018/11/9 10:50
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    /**
     * 添加用户
     * @param
     * @return
     */
    @Override
    public User addUser(String userName,String password) {
        User user = new User();
        user.setName(userName);
        user.setPassword(Integer.valueOf(password));
        userRepository.save(user);

        return user;
    }

    /**
     * 添加角色
     * @param
     * @return
     */
    @Override
    public Role addRole(Integer userId,String roleName) {
        Optional<User> optional = userRepository.findById(Long.valueOf(userId));
        User user = optional.get();

        Role role = new Role();
        role.setRoleName(roleName);
        role.setUser(user);

        Permission permission1 = new Permission();
        permission1.setPermission("create");
        permission1.setRole(role);

        Permission permission2 = new Permission();
        permission2.setPermission("update");
        permission2.setRole(role);

        List<Permission> permissionList =   new ArrayList<>();
        permissionList.add(permission1);
        permissionList.add(permission2);

        role.setPermissions(permissionList);

        roleRepository.save(role);

        return role;
    }

    /**
     * 查找用户
     * @param name
     * @return
     */
    @Override
    public User findByName(String name) {

        return userRepository.findByName(name);
    }
}
