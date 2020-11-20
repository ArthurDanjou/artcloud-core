package fr.arthurdanjou.artcloud.common.messaging;

import com.rabbitmq.client.*;
import fr.arthurdanjou.artcloud.ArtCloud;
import fr.arthurdanjou.artcloud.common.managers.PacketManager;

import java.nio.charset.StandardCharsets;

public class MessagingCallbackHandler extends DefaultConsumer {

    private final ArtCloud artCloud;
    private final PacketManager packetManager;

    public MessagingCallbackHandler(ArtCloud artCloud, PacketManager packetManager) {
        super(null);
        this.artCloud = artCloud;
        this.packetManager = packetManager;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
        String message = new String(body, StandardCharsets.UTF_8);
        try {
            this.packetManager.handleMessage(this.artCloud.getGson().fromJson(message, ArtPacket.class));
        } catch (Exception e) {
            this.artCloud.getLogger().error("Impossible d'appeler un packet !");
        }
    }

    public void handle(String s, Delivery delivery) {

    }
}
