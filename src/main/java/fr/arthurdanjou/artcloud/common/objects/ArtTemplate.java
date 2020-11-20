package fr.arthurdanjou.artcloud.common.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @AllArgsConstructor
@NoArgsConstructor
public class ArtTemplate {

    private String name;
    private String serviceName;
    private boolean startOnStartup;

    private String imageId;
    private String hostname;

    private int replicaCount;
    private boolean persistent;

    private int maxMemory;

    private int publishedPort;
    private int targetPort;

}
