package com.lyl.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author 【 木子雷 】 公众号
 * @PACKAGE_NAME: com.lyl.service
 * @ClassName: RedisService
 * @Description: 操作 redis 的工具类
 * @Date: 2020-11-09 21:30
 **/
@Service("redisService")
public class RedisService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RedisTemplate redisTemplate;


    private static final Long SUCCESS = 1L;

    /**
     * 释放分布式锁 Lua 脚本
     */
    private final String releaseDistributedLocakLua = "if redis.call('get', KEYS[1]) == ARGV[1] " +
            "then " +
            "return redis.call('del', KEYS[1]) " +
            "else " +
            "return 0 " +
            "end";

    /**
     * 获取分布式锁 Lua 脚本
     */
    private final String getDistributedLocakLua = "if redis.call('setNx',KEYS[1],ARGV[1])  then " +
            "   if redis.call('get',KEYS[1])==ARGV[1] then " +
            "      return redis.call('expire',KEYS[1],ARGV[2]) " +
            "   else " +
            "      return 0 " +
            "   end " +
            "end";


    /**
     * 获取分布式锁
     *
     * @param lockKey
     * @param lockValue
     * @param expireTime
     * @return
     */
    public boolean tryGetDistributedLock(final String lockKey, final String lockValue, int expireTime) {

        // redis脚本，执行脚本的返回类型为 Long
        RedisScript<Long> redisScript = new DefaultRedisScript<>(getDistributedLocakLua, Long.class);

        // 对非string类型的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey),
                lockValue, String.valueOf(expireTime));

        if (SUCCESS.equals(result)) {
            return true;
        }

        return false;
    }


    /**
     * 释放分布式锁
     *
     * @param lockKey
     * @param lockValue
     * @return
     */
    public synchronized boolean releaseDistributedLock(final String lockKey, final String lockValue) {

        // redis脚本，执行脚本的返回类型为 Long
        RedisScript<Long> redisScript = new DefaultRedisScript<>(releaseDistributedLocakLua, Long.class);

        // 对非string类型的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        try {
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), lockValue);
            if (SUCCESS.equals(result)) {
                return true;
            }
        } catch (Exception e) {
            logger.error("releaseDistributedLock fail : ", e);
        }
        return false;
    }

}
