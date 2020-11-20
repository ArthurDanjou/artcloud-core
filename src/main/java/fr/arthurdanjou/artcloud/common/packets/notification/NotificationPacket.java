package fr.arthurdanjou.artcloud.common.packets.notification;

import fr.arthurdanjou.artcloud.common.messaging.ArtPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class NotificationPacket implements ArtPacket {

    private final String title;
    private final String content;

}
