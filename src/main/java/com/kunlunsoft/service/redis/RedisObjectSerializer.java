package com.kunlunsoft.service.redis;

import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.Serializable;

public class RedisObjectSerializer implements RedisSerializer<Object> {
    public static final Logger logger = Logger.getLogger(RedisObjectSerializer.class);
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();
    static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public Object deserialize(byte[] bytes) {
        if (isEmpty(bytes)) {
            return null;
        }
        try {
            return deserializer.convert(bytes);
        } catch (Exception ex) {
            throw new SerializationException("Cannot deserialize", ex);
        }
    }

    @Override
    public byte[] serialize(Object object) {
        if (object == null) {
            return EMPTY_ARRAY;
        }
        if (!(object instanceof Serializable)) {
            String msg = "RedisObjectSerializer.serialize()" + object + " 必须实现接口java.io.Serializable";
            logger.error(msg);
            System.out.println("msg :" + msg);
        }
        try {
            return serializer.convert(object);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("serializer.convert error", ex);
            return EMPTY_ARRAY;
        }
    }

    private boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
}
