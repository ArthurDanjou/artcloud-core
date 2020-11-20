package fr.arthurdanjou.artcloud.common.packets.client.registration;

import fr.arthurdanjou.artcloud.common.messaging.ArtPacket;
import fr.arthurdanjou.artcloud.common.objects.AppClient;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class ClientRegisterPacket implements ArtPacket {

    private final AppClient client;

}
