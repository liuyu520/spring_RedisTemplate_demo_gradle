package com.kunlunsoft.service.redis;

import org.apache.log4j.Logger;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.io.*;
import java.nio.ByteBuffer;

public class RedisLongSerializer implements RedisSerializer<Long> {
    public static final Logger logger = Logger.getLogger(RedisLongSerializer.class);
    static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public Long deserialize(byte[] bytes) {
        if (isEmpty(bytes)) {
            return null;
        }
        return convertByteArrayToLong2(bytes);
    }

    @Override
    public byte[] serialize(Long object) {
        if (object == null) {
            return EMPTY_ARRAY;
        }

        try {
            longToByteArray(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EMPTY_ARRAY;
    }

    private byte[] longToByteArray(final long i) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeLong(i);
        dos.flush();
        byte[] bytes = bos.toByteArray();
        bos.close();
        return bytes;
    }

    /***
     * test ok, <br />
     *
     * @param longBytes
     * @return
     */
    private long convertByteArrayToLong(byte[] longBytes) {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(longBytes);
        DataInputStream dos = new DataInputStream(byteArrayInputStream);
        try {
            return dos.readLong();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    private long convertByteArrayToLong2(byte[] longBytes) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(Long.BYTES);
        byteBuffer.put(longBytes);
        byteBuffer.flip();
        return byteBuffer.getLong();
    }


    private boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
}
