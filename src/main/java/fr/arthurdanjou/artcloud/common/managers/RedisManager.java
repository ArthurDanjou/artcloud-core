package fr.arthurdanjou.artcloud.common.managers;

import fr.arthurdanjou.artcloud.ArtCloud;
import fr.arthurdanjou.artcloud.common.configuration.needed.RedisConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Pipeline;

import java.util.List;
import java.util.Set;

public class RedisManager extends AbstractManager {

    private JedisPool jedisPool;
    private final RedisConfig redisConfig;

    public RedisManager(ArtCloud artCloud, RedisConfig redisConfig) {
        super(artCloud, "Redis");
        this.redisConfig = redisConfig;
    }

    @Override
    public void enable() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(-1);
        jedisPoolConfig.setJmxEnabled(false);
        jedisPoolConfig.setMaxIdle(10);

        this.jedisPool = new JedisPool(
                jedisPoolConfig,
                redisConfig.getHost(),
                redisConfig.getPort(),
                redisConfig.getTimeout(),
                redisConfig.getPassword()
        );
        connect();
    }

    @Override
    public void disable() {
        this.artCloud.getLogger().info("Déconnection à Redis !");
        this.jedisPool.close();
        this.jedisPool = null;
    }

    public void connect() {
        try {
            this.jedisPool.getResource();
            this.artCloud.getLogger().info("Connecté à Redis !");
        } catch (Exception e) {
            this.artCloud.getLogger().error("Impossible de se connecter à Redis !");
            e.printStackTrace();
        }
    }

    public Jedis getResource() {
        return this.jedisPool.getResource();
    }

    public Pipeline pipelined() {
        return this.jedisPool.getResource().pipelined();
    }

    public void set(String key, String value) {
        this.getResource().set(key, value);
    }

    public String get(String key) {
        return this.getResource().get(key);
    }

    public List<Object> getValues(List<String> keys) {
        Pipeline pipeline = this.pipelined();
        keys.forEach(pipeline::get);
        return pipeline.syncAndReturnAll();
    }

    public List<Object> getAll() {
        Pipeline pipeline = this.pipelined();
        Set<String> keys = this.getResource().keys("'*'");
        keys.forEach(pipeline::get);
        return pipeline.syncAndReturnAll();
    }
}
