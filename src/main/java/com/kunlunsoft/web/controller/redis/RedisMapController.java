package com.kunlunsoft.web.controller.redis;

import com.common.bean.BaseResponseDto;
import com.common.util.SystemHWUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/redis/map", produces = SystemHWUtil.RESPONSE_CONTENTTYPE_JSON_UTF)
public class RedisMapController {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /***
     * Map 操作
     * @param model
     * @param request
     * @param response
     * @param id
     * @param key
     * @param value
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add/json", produces = SystemHWUtil.RESPONSE_CONTENTTYPE_JSON_UTF)
    public String jsonHashAdd3(Model model, HttpServletRequest request, HttpServletResponse response
            , @RequestParam(required = false) String id, String key, String value) {
        // 与 RedisTemplateUtil.getValueList(stringRedisTemplate, key) 效果一样
        stringRedisTemplate.opsForHash().put(id, key, value);
        return BaseResponseDto.put2("id", id)
                .put("key", key)
                .put("value", value)
                .toJson();
    }

    /***
     * Map 操作
     * @param model
     * @param request
     * @param response
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/query/json", produces = SystemHWUtil.RESPONSE_CONTENTTYPE_JSON_UTF)
    public String jsonQueryHash2(Model model, HttpServletRequest request, HttpServletResponse response
            , @RequestParam(required = false) String id) {
        Map map = stringRedisTemplate.opsForHash().entries(id);
        return BaseResponseDto.jsonValue(map);
    }
}
