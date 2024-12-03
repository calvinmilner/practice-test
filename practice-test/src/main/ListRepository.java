package vttp.ssf.practice_test.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ListRepository {
    
    @Autowired @Qualifier("redis-0")
    RedisTemplate<String, String> template;

    public void leftPush(String redisKey, String value) {
        template.opsForList().leftPush(redisKey, value);
    }

    public void rightPush(String redisKey, String value) {
        template.opsForList().rightPush(redisKey, value);
    }

    public void leftPop(String redisKey) {
        template.opsForList().leftPop(redisKey);
    }

    public void rightPop(String redisKey) {
        template.opsForList().rightPop(redisKey);
    }

    public Long size(String redisKey) {
        return template.opsForList().size(redisKey);
    }

    public String get(String redisKey, Integer index) {
        return template.opsForList().index(redisKey, index);
    }

    public List<String> getList(String redisKey) {
        return template.opsForList().range(redisKey, 0, -1);
    }
}