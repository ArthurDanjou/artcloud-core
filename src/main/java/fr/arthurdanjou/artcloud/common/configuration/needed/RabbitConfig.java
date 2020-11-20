package fr.arthurdanjou.artcloud.common.configuration.needed;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class RabbitConfig {

    private final String host;
    private final int port;
    private final String username;
    private final String password;

}
