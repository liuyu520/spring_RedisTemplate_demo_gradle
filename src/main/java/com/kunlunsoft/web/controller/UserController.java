package com.kunlunsoft.web.controller;

import com.io.hw.json.HWJacksonUtils;
import com.kunlunsoft.config.Blog;
import com.kunlunsoft.entity.User;
import com.kunlunsoft.mapper.user.UserMapper;
import com.kunlunsoft.service.UserService;
import com.string.widget.util.ValueWidget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping(value = "/user"/*, produces = SystemHWUtil.RESPONSE_CONTENTTYPE_JSON_UTF*/)
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @ResponseBody
    @RequestMapping(value = "/query/json")
    public String json2(Model model, HttpServletRequest request, HttpServletResponse response
    ,String name) {
        List<User> users = userService.findByName(name);
        if (ValueWidget.isNullOrEmpty(users)) {
            userMapper.insertByNameAndAge("AAA", new Random().nextInt(10));
        }
        Blog blog = new Blog();
        blog.setDesc222("aaa");
        return  HWJacksonUtils.getJsonP(users);
    }
}
