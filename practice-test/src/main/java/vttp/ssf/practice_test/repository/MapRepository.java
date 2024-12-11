package vttp.ssf.practice_test.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import vttp.ssf.practice_test.model.Product;

@Repository
public class MapRepository {

    @Autowired @Qualifier("redis-0")
    RedisTemplate<String,String> redisTemplate;

     public void createHash(String key, String hashKey, String value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    // day 15 - slide 37
    public String get(String key, String hashKey) {
        Object objValue = redisTemplate.opsForHash().get(key, hashKey);
        return objValue.toString();
    }

    // day 15 - slide 38
    public void deleteHashKey(String key, String hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    // day 15 - slide 39
    public Boolean hashKeyExists(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    // day 15 - slide 40
    // List<Object> values = template.opsForHash().values("c01")l
    public List<Object> getValues(String key) {
        List<Object> values = redisTemplate.opsForHash().values(key);
        return values;
    }

    // day 15 - slide 41
    // long mapSize= template.opsForHash().size(“c01”);
    public Long size(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    public List<Product> getAllProducts() {
        List<Product> productList = new LinkedList<>();
        Map<Integer, Object> map = new HashMap<>();

        for(Map.Entry<Object,Object> entry : redisTemplate.opsForHash().entries("products").entrySet())
            map.put(Integer.parseInt(entry.getKey().toString()), entry.getValue());
            
            for(int key : map.keySet()) {
                String json = map.get(key).toString();
                productList.add(Product.jsonToProduct(json));
            }
        return productList;
    }

    public Product getProductById(int id) {
        String value = redisTemplate.opsForHash().get("products", String.valueOf(id)).toString();
        return Product.jsonToProduct(value);
    }

    public void updateProduct(Product p) {
        redisTemplate.opsForHash().put("products", String.valueOf(p.getId()), p.toJsonString());
    }
}
