package fr.arthurdanjou.artcloud.common.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter @AllArgsConstructor
public class DockerImage implements Serializable {

    private final String id;
    private final String tag;

}
