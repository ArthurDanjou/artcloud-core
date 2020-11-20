package fr.arthurdanjou.artcloud.common.packets.log;

import fr.arthurdanjou.artcloud.common.messaging.ArtPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class LogPacket extends ArtPacket {

    private final String fileName;
    private final String line;

}
