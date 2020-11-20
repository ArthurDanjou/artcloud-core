package fr.arthurdanjou.artcloud.common.packets.master;

import fr.arthurdanjou.artcloud.common.messaging.ArtPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class MasterPurgeDataPacket implements ArtPacket {

    private final String reason;

}
