package fr.arthurdanjou.artcloud.common.packets.service.response;

import fr.arthurdanjou.artcloud.common.messaging.ArtPacket;
import fr.arthurdanjou.artcloud.common.objects.ArtService;
import fr.arthurdanjou.artcloud.common.packets.ResponseStatus;
import fr.arthurdanjou.artcloud.common.packets.service.RequestType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HttpServiceResponseEvent implements ArtPacket {

    private final ResponseStatus status;
    private final RequestType type;
    private final ArtService service;
    private final String correlationId;

}
