package fr.arthurdanjou.artcloud.common.packets.client.data;

import fr.arthurdanjou.artcloud.common.messaging.ArtPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @AllArgsConstructor @Setter
public class ClientResponseDataPacket implements ArtPacket {

    private final UUID uuid;
    private final int freeMemory;
    private final int cpuLoad;
    private final String correlationId;

}
