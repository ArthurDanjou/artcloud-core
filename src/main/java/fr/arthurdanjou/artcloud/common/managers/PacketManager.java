package fr.arthurdanjou.artcloud.common.managers;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import fr.arthurdanjou.artcloud.ArtCloud;
import fr.arthurdanjou.artcloud.common.messaging.*;
import lombok.Getter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@Getter
public class PacketManager extends AbstractManager {

    @Getter
    public Set<ArtCloudListener> listeners;
    private final RabbitManager connection;

    private final Set<String> queues = new HashSet<>();

    public PacketManager(ArtCloud artCloud, RabbitManager connection) {
        super(artCloud, "Packet");
        this.listeners = new HashSet<>();
        this.connection = connection;
    }

    @Override
    public void enable() {
        try {
            for (MessagingExchange exchange : MessagingExchange.values()) {
                String queueName = "process." + exchange.getName();
                this.connection.getChannel().exchangeDeclare(exchange.getName(), BuiltinExchangeType.DIRECT, true, false, false, null);
                this.connection.getChannel().queueDeclare(queueName, true, true, true, null);
                this.bindQueue(exchange.getName(), queueName, exchange.getName());
                this.consumeQueue(exchange.getName());
            }
        } catch (IOException e) {
            this.artCloud.getLogger().error("Impossible d'initialiser la queue dans RabbitMQ !");
            this.artCloud.getLogger().fatal(e.getMessage());
        }
    }

    @Override
    public void disable() {

    }

    public void initQueue(UUID uuid) throws IOException {
        String queueName = "process." + MessagingExchange.CLIENTS.getName() + "." + uuid.toString();
        this.connection.getChannel().queueDeclare(queueName, true, true, true, null);
        this.bindQueue(MessagingExchange.CLIENTS.getName(), "process." + MessagingExchange.CLIENTS.getName() + "." + uuid.toString(), uuid.toString());
    }

    private void consumeQueue(String queueName) throws IOException {
        if (queues.contains(queueName)) return;
        this.connection.getChannel().basicConsume(queueName, true, new MessagingCallbackHandler(artCloud, this));
        queues.add(queueName);
    }

    private void bindQueue(String exchange, String queue, String key) throws IOException {
        this.getConnection().getChannel().queueBind(queue, exchange, key);
    }

    public void registerListener(ArtCloudListener listener) {
        this.listeners.add(listener);
    }

    public void unregisterListener(ArtCloudListener listener) {
        this.listeners.remove(listener);
    }

    public void registerListeners(ArtCloudListener... listenersList) {
        this.listeners.addAll(Arrays.asList(listenersList));
    }

    public void handleMessage(ArtPacket packet) throws Exception {
        if (packet == null) return;
        for (ArtCloudListener listener : this.getListeners()) {
            for (Method method : listener.getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(ArtPacketHandler.class)) {
                    if (method.getParameterTypes().length == 1
                            && (method.getParameterTypes()[0].getSuperclass() == ArtPacket.class
                            && method.getParameterTypes()[0] == packet.getClass()
                            || method.getParameterTypes()[0] == ArtPacket.class)) {
                        method.invoke(listener, packet);
                    }
                }
            }
        }
    }

    public PacketManager send(MessagingExchange exchange, UUID uuid, ArtPacket packet) {
        try {
            this.connection.getChannel().basicPublish(exchange.getName(), "process." + MessagingExchange.CLIENTS.getName() + "." + uuid.toString(), new AMQP.BasicProperties(), this.artCloud.getGson().toJson(packet).getBytes());
            return this;
        } catch (IOException e) {
            this.artCloud.getLogger().error("Impossible d'envoyer un event à RabbitMQ !");
            this.artCloud.getLogger().fatal(e.getMessage());
        }
        return null;
    }

    public PacketManager send(MessagingExchange exchange, ArtPacket packet) {
        try {
            this.connection.getChannel().basicPublish(exchange.getName(), exchange.getName(), new AMQP.BasicProperties(), this.artCloud.getGson().toJson(packet).getBytes());
            return this;
        } catch (IOException e) {
            this.artCloud.getLogger().error("Impossible d'envoyer un event à RabbitMQ !");
            this.artCloud.getLogger().fatal(e.getMessage());
        }
        return null;
    }

    public void receive(Consumer<? extends ArtPacketResponse> consumer) {

    }

    /*
    *
    * packetManager.send(HeartBeatPacket.class).receive((response) -> {
    *   response.getData...
    * });
    *
    * packageManager.send(HeartBeatPacket.class);
    *
    * */
}
