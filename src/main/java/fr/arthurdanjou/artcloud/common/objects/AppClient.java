package fr.arthurdanjou.artcloud.common.objects;

import fr.arthurdanjou.artcloud.ArtCloud;
import fr.arthurdanjou.artcloud.common.messaging.MessagingExchange;
import fr.arthurdanjou.artcloud.common.packets.client.ClientShutdownPacket;
import fr.arthurdanjou.artcloud.common.packets.client.data.AskForClientDataPacket;
import fr.arthurdanjou.artcloud.common.packets.client.data.ClientResponseDataPacket;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter @Setter
public class AppClient implements Serializable {

    private final ArtCloud artCloud;
    private final UUID uuid;
    private String ip;
    private final int maxMemory;
    private int cpuLoad;
    private int freeMemory;
    private Long lastUpdate;

    private final Map<String, ArtService> services;

    public AppClient(ArtCloud artCloud, UUID uuid) {
        this.artCloud = artCloud;
        this.uuid = uuid;
        this.lastUpdate = System.currentTimeMillis();
        this.maxMemory = this.artCloud.getConfiguration().getMaxMemory();
        this.cpuLoad = 0;
        this.freeMemory = maxMemory - cpuLoad;
        this.services = new HashMap<>();
    }

    public int getFreeMemory() {
        return maxMemory - cpuLoad;
    }

    public void shutdown() {
        this.artCloud.getPacketManager().publishMessage(MessagingExchange.CLIENTS, new ClientShutdownPacket());
    }

    public void askForUpdate() {
        this.artCloud.getPacketManager().publishMessage(MessagingExchange.CLIENTS, this.uuid, new AskForClientDataPacket());
    }

    public void update(ClientResponseDataPacket packet) {
        this.lastUpdate = System.currentTimeMillis();
        this.freeMemory = packet.getFreeMemory();
        this.cpuLoad = packet.getCpuLoad();

        if (getCpuLoad() != packet.getCpuLoad()) {
            this.artCloud.getLogger().warn("Client & Master non synchronisés ! (Client: " + getCpuLoad() + ", Master: " + packet.getCpuLoad());
        }
    }

}
