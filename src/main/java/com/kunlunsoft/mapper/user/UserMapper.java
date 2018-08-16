package com.kunlunsoft.mapper.user;

import com.kunlunsoft.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    void insertSelective(User user);

    List<User> findByName(@Param("name") String name);

    String findNameById(@Param("id") Integer id);



    void insert(User user);

    void insertByNameAndAge(@Param("name") String name, @Param("age") int age);
}
