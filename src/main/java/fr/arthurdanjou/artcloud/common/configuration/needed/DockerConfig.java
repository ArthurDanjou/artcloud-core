package fr.arthurdanjou.artcloud.common.configuration.needed;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class DockerConfig {

    private String host;
    private int port;

    private String fileUser;

    private String username;
    private String email;
    private String password;
    private String url;

    private String networkName;

    private String repositoryName;

}
