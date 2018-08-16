package com.kunlunsoft.service;

import com.kunlunsoft.entity.User;
import com.kunlunsoft.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
//    @Cacheable(cacheNames = "user:query")
    public List<User> findByName( String name){
        return this.userMapper.findByName(name);
    }
}
