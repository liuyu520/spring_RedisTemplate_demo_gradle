package com.kunlunsoft.web.controller.redis;

import com.common.bean.BaseResponseDto;
import com.common.util.SystemHWUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/redis/long/map", produces = SystemHWUtil.RESPONSE_CONTENTTYPE_JSON_UTF)
public class RedisLongMapController {
    @Resource(name = "longRedisTemplate")
    private RedisTemplate<String, Long> redisTemplate;

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
            , @RequestParam(required = false) String id, String key, Integer value) {
        // 与 RedisTemplateUtil.getValueList(stringRedisTemplate, key) 效果一样
        redisTemplate.opsForHash().put(id, key, value);
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
        Map map = redisTemplate.opsForHash().entries(id);
        return BaseResponseDto.jsonValue(map);
    }
}
