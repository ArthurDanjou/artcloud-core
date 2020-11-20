package fr.arthurdanjou.artcloud.common.packets.service.request;

import fr.arthurdanjou.artcloud.common.messaging.ArtPacket;
import fr.arthurdanjou.artcloud.common.objects.ArtService;
import fr.arthurdanjou.artcloud.common.packets.service.RequestType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class ServiceRequestEvent implements ArtPacket {

    private final RequestType type;
    private final ArtService service;

}
