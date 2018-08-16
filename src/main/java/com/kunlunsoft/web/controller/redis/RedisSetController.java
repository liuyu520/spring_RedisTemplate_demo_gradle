package com.kunlunsoft.web.controller.redis;

import com.alibaba.fastjson.JSON;
import com.kunlunsoft.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class RedisSetController {
    @Autowired
    private RedisTemplate<String, User> redisTemplate;

    @RequestMapping("/set")
    public String save2Redis(@RequestParam(name = "key", required = false) String key) {
// 保存对象
        User user = new User();
        user.setName("超人");
//        user.setAge(20);
        redisTemplate.opsForValue().set(key, user);
        return key;

    }

    @RequestMapping("/get")
    public String get4Redis(@RequestParam(name = "key", required = false) String key) {
        User user = redisTemplate.opsForValue().get(key);
        if (null == user) {
            return "未获取到";
        }
        return JSON.toJSON(user).toString();
//        return "redis get";
//        return HWJacksonUtils.getJsonP(redisTemplate.opsForValue().get(key))/*.getNickname()*/;
    }
}
