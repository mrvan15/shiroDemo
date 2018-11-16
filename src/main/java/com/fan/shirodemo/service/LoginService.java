package com.fan.shirodemo.service;


import com.fan.shirodemo.entity.Role;
import com.fan.shirodemo.entity.User;
import com.fan.shirodemo.repository.RoleRepository;
import com.fan.shirodemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public interface  LoginService {

  public User addUser(String userName,String password);

  public Role addRole(Integer userId,String roleName);

  public User findByName(String name);

}
