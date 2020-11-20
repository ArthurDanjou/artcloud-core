package fr.arthurdanjou.artcloud.common.configuration.needed;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class RedisConfig {

    private final String host;
    private final int port;
    private final int timeout;
    private final String password;

}
