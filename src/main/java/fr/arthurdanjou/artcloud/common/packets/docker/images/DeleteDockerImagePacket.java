package fr.arthurdanjou.artcloud.common.packets.docker.images;

import fr.arthurdanjou.artcloud.common.messaging.ArtPacket;
import fr.arthurdanjou.artcloud.common.objects.DockerImage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class DeleteDockerImagePacket implements ArtPacket {

    private final DockerImage image;

}
