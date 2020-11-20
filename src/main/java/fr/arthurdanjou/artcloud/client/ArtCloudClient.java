package fr.arthurdanjou.artcloud.client;

import fr.arthurdanjou.artcloud.ArtCloud;

import java.io.IOException;

public class ArtCloudClient extends ArtCloud {

    public ArtCloudClient() throws IOException {
        super("Client");
        this.getPacketManager().initQueue(this.getUuid());
        System.out.println("E");
    }

    @Override
    public void enable() {
        System.out.println("Yo");
    }

    @Override
    public void disable() {

    }

    @Override
    public void registerListeners() {

    }

    @Override
    public void registerCommands() {

    }
}
