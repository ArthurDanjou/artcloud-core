package fr.arthurdanjou.artcloud.common.configuration;

import fr.arthurdanjou.artcloud.common.configuration.needed.DockerConfig;
import fr.arthurdanjou.artcloud.common.configuration.needed.RabbitConfig;
import fr.arthurdanjou.artcloud.common.configuration.needed.RedisConfig;
import lombok.Getter;

@Getter
public class Configuration {

    private final RedisConfig redisConfig;
    private final RabbitConfig rabbitConfig;
    private final DockerConfig dockerConfig;
    private final int maxMemory;
    private final int dailyRebootTime;

    public Configuration() {
        this.redisConfig = new RedisConfig("127.0.0.1", 6379, 5000, "");
        this.rabbitConfig = new RabbitConfig("127.0.0.1", 5672, "", "");
        this.dockerConfig = new DockerConfig("127.0.0.1", 2376, "", "", "", "", "jarvis", "", "");
        this.maxMemory = 4000;
        this.dailyRebootTime = 2;
    }
}
