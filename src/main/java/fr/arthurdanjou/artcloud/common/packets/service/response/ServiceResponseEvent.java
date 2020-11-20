package fr.arthurdanjou.artcloud.common.packets.service.response;

import fr.arthurdanjou.artcloud.common.messaging.ArtPacket;
import fr.arthurdanjou.artcloud.common.objects.ArtService;
import fr.arthurdanjou.artcloud.common.packets.ResponseStatus;
import fr.arthurdanjou.artcloud.common.packets.service.RequestType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ServiceResponseEvent implements ArtPacket {

    private ResponseStatus status;
    private RequestType type;
    private ArtService service;
    private String correlationId;

}
