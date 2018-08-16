package com.kunlunsoft.web.controller.redis;

import com.common.bean.BaseResponseDto;
import com.common.util.SystemHWUtil;
import com.kunlunsoft.util.RedisTemplateUtil;
import com.string.widget.util.ValueWidget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(value = "/redis", produces = SystemHWUtil.RESPONSE_CONTENTTYPE_JSON_UTF)
public class RedisListController {
    public static final String PUSH_LIMIT_REDIS_KEY = "push_limit";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @ResponseBody
    @RequestMapping(value = "/add/json", produces = SystemHWUtil.RESPONSE_CONTENTTYPE_JSON_UTF)
    public String jsonAdd2(Model model, HttpServletRequest request, HttpServletResponse response
            , @RequestParam(required = false) String val) {
        if (ValueWidget.isNullOrEmpty(val)) {
            val = String.valueOf(System.currentTimeMillis());
        }
        this.stringRedisTemplate.opsForList().rightPush(PUSH_LIMIT_REDIS_KEY, val);
        return BaseResponseDto.jsonValue(val);
    }

    /***
     *  获取 list
     * @param model
     * @param request
     * @param response
     * @param demo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/query/json", produces = SystemHWUtil.RESPONSE_CONTENTTYPE_JSON_UTF)
    public String jsonQuery2(Model model, HttpServletRequest request, HttpServletResponse response
            , @RequestParam(required = false) String demo) {
        String key = PUSH_LIMIT_REDIS_KEY;
        List<String> vals = RedisTemplateUtil.getValueList(stringRedisTemplate, key);
        return BaseResponseDto.jsonValue(vals);
    }

    @ResponseBody
    @RequestMapping(value = "/query2/json", produces = SystemHWUtil.RESPONSE_CONTENTTYPE_JSON_UTF)
    public String jsonQuery3(Model model, HttpServletRequest request, HttpServletResponse response
            , @RequestParam(required = false) String demo) {
        String key = PUSH_LIMIT_REDIS_KEY;
        // 与 RedisTemplateUtil.getValueList(stringRedisTemplate, key) 效果一样
        List<String> vals = stringRedisTemplate.boundListOps(key).range(0, -1);
        return BaseResponseDto.jsonValue(vals);
    }
}
